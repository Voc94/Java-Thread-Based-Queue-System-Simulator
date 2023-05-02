package simulatoare;

import modele.Client;
import modele.Coada;
import strategy.Scheduler;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SimulationFrame extends JFrame {
    private Scheduler scheduler;
    private int numberOfQueues;
    private List<Coada> queues;
    private List<Client> waitingClients;
    private JTextArea logArea;
    private PrintWriter writer;

    public SimulationFrame(Scheduler scheduler, List<Client> waitingClients) {
        this.scheduler = scheduler;
        this.numberOfQueues = scheduler.getQueues().size();
        this.queues = scheduler.getQueues();
        this.waitingClients = waitingClients;

        setTitle("Queue Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        //layout
        setLayout(new BorderLayout());

        //new text area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        // add logScrollPane to JFrame
        add(logScrollPane, BorderLayout.CENTER);

        // PrintWriter false
        try {
            writer = new PrintWriter("simulation_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateUI(int currentTime) {

        // update waiting clients
        StringBuilder waitingClientsStringBuilder = new StringBuilder("Waiting clients: ");
        for (Client client : waitingClients) {
            waitingClientsStringBuilder.append("(");
            waitingClientsStringBuilder.append(client.getID());
            waitingClientsStringBuilder.append(",");
            waitingClientsStringBuilder.append(client.getArrivalTime());
            waitingClientsStringBuilder.append(",");
            waitingClientsStringBuilder.append(client.getServiceTime());
            waitingClientsStringBuilder.append("); ");
        }

        // update queues
        StringBuilder queueStatus = new StringBuilder();
        int queueNumber = 1;
        for (Coada queue : scheduler.getQueues()) {
            queueStatus.append("Queue ").append(queueNumber).append(": ");
            synchronized (queue) {
                for (Client client : queue.getClienti()) {
                    queueStatus.append("(" + client.getID() + "," + client.getArrivalTime() + "," + client.getServiceTime() + ")");
                    queueStatus.append(", ");
                }
            }
            if (queue.getClienti().isEmpty()) {
                queueStatus.append("closed");
            }
            queueStatus.append("\n");
            queueNumber++;
        }

        // update log
        StringBuilder logStringBuilder = new StringBuilder();
        logStringBuilder.append("Time ").append(currentTime).append("\n");
        logStringBuilder.append(waitingClientsStringBuilder.toString()).append("\n");
        logStringBuilder.append(queueStatus.toString()).append("\n");
        logArea.append(logStringBuilder.toString());

        // Append the log to true
        writer.append(logStringBuilder.toString());
        writer.flush();
    }
    //close
    @Override
    public void dispose() {
        super.dispose();
        writer.close();
    }
}

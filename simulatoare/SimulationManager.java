package simulatoare;
import modele.Coada;
import modele.Client;
import strategy.Scheduler;
import strategy.SelectionPolicy;

import java.util.*;
public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private int minArrivalTime;
    private int maxArrivalTime;
    private SelectionPolicy selectionPolicy;

    private Scheduler scheduler;

    private SimulationFrame frame;

    private List<Client> generatedTasks;
    private List<Client> waitingClients;

    public SimulationManager(int timeLimit, int minProcessingTime, int maxProcessingTime,
                             int numberOfServers, int numberOfClients, int minArrivalTime, int maxArrivalTime, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.selectionPolicy = selectionPolicy;

        this.generatedTasks = generateNRandomTasks();
        this.waitingClients = new ArrayList<>(generatedTasks);

        this.scheduler = new Scheduler(numberOfServers, numberOfClients, selectionPolicy);
        this.frame = new SimulationFrame(scheduler, waitingClients);
        this.frame.setVisible(true);
    }

    private List<Client> generateNRandomTasks() {
        List<Client> tasks = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfClients; i++) {
            int arrivalTime = minArrivalTime + random.nextInt(maxArrivalTime - minArrivalTime + 1);
            int serviceTime = minProcessingTime + random.nextInt(maxProcessingTime - minProcessingTime + 1);
            tasks.add(new Client(i, arrivalTime, serviceTime));
        }

        tasks.sort(Comparator.comparingInt(Client::getArrivalTime));
        return tasks;
    }
    public void run() {
        int currentTime = 0;
        while (currentTime <= timeLimit) {
            for (Coada coada : scheduler.getQueues()) {
                for (Client client : coada.getClienti()) {
                    int serviceTime = client.getServiceTime();
                    if (serviceTime > 1) {
                        client.setServiceTime(serviceTime - 1);
                    } else {
                        coada.pollClient();
                        coada.getWaitingPeriod().addAndGet(-1 * serviceTime);
                    }
                }
            }

            List<Client> dispatchedClients = new ArrayList<>();
            for (Client client : waitingClients) {
                if (client.getArrivalTime() <= currentTime) {
                    scheduler.dispatchTask(client);
                    dispatchedClients.add(client);
                }
            }
            waitingClients.removeAll(dispatchedClients);


            frame.updateUI(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000); //time pass
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
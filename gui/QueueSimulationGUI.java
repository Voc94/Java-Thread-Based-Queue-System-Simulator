    package gui;

    import strategy.SelectionPolicy;
    import simulatoare.SimulationManager;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    public class QueueSimulationGUI extends JFrame {
        private JComboBox<String> strategyComboBox;

        public QueueSimulationGUI() {
            setTitle("Queue Simulation");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // modified to Y_AXIS
            add(mainPanel);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(8, 2, 5, 5));
            mainPanel.add(inputPanel);

            JLabel clientsLabel = new JLabel("Number of clients:");
            JTextField clientsField = new JTextField(5);
            inputPanel.add(clientsLabel);
            inputPanel.add(clientsField);

            JLabel queuesLabel = new JLabel("Number of queues:");
            JTextField queuesField = new JTextField(5);
            inputPanel.add(queuesLabel);
            inputPanel.add(queuesField);

            JLabel timeLimitLabel = new JLabel("Time limit:");
            JTextField timeLimitField = new JTextField(5);
            inputPanel.add(timeLimitLabel);
            inputPanel.add(timeLimitField);

            JLabel minArrivalTimeLabel = new JLabel("Min arrival time:");
            JTextField minArrivalTimeField = new JTextField(5);
            inputPanel.add(minArrivalTimeLabel);
            inputPanel.add(minArrivalTimeField);

            JLabel maxArrivalTimeLabel = new JLabel("Max arrival time:");
            JTextField maxArrivalTimeField = new JTextField(5);
            inputPanel.add(maxArrivalTimeLabel);
            inputPanel.add(maxArrivalTimeField);

            JLabel minProcessingTimeLabel = new JLabel("Min processing time:");
            JTextField minProcessingTimeField = new JTextField(5);
            inputPanel.add(minProcessingTimeLabel);
            inputPanel.add(minProcessingTimeField);

            JLabel maxProcessingTimeLabel = new JLabel("Max processing time:");
            JTextField maxProcessingTimeField = new JTextField(5);
            inputPanel.add(maxProcessingTimeLabel);
            inputPanel.add(maxProcessingTimeField);

            JLabel strategyLabel = new JLabel("Strategy:");
            String[] strategyOptions = {"Shortest Queue", "Shortest Time"};
            strategyComboBox = new JComboBox<>(strategyOptions);
            inputPanel.add(strategyLabel);
            inputPanel.add(strategyComboBox);

            JButton startButton = new JButton("Start Simulation");
            mainPanel.add(startButton);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int numberOfClients = Integer.parseInt(clientsField.getText());
                    int numberOfServers = Integer.parseInt(queuesField.getText());
                    int timeLimit = Integer.parseInt(timeLimitField.getText());
                    int minArrivalTime = Integer.parseInt(minArrivalTimeField.getText());
                    int maxArrivalTime = Integer.parseInt(maxArrivalTimeField.getText());
                    int minProcessingTime = Integer.parseInt(minProcessingTimeField.getText());
                    int maxProcessingTime = Integer.parseInt(maxProcessingTimeField.getText());
                    SelectionPolicy selectionPolicy;
                    if (strategyComboBox.getSelectedIndex() == 0) {
                        selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                    } else {
                        selectionPolicy = SelectionPolicy.SHORTEST_TIME;
                    }

                    SimulationManager simulationManager = new SimulationManager(timeLimit, minProcessingTime, maxProcessingTime,
                            numberOfServers, numberOfClients, minArrivalTime, maxArrivalTime, selectionPolicy);
                    Thread simulationThread = new Thread(simulationManager);
                    simulationThread.start();
                }
            });
        }
    }
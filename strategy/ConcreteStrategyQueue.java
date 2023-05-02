package strategy;

import modele.Client;
import modele.Coada;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public void addTask(List<Coada> cozi, Client client) {
        synchronized (cozi) {
            int shortestQueueIndex = -1;
            int minClients = Integer.MAX_VALUE;

            // find the queue
            for (int i = 0; i < cozi.size(); i++) {
                Coada coada = cozi.get(i);
                if (coada.getClienti().size() < minClients) {
                    minClients = coada.getClienti().size();
                    shortestQueueIndex = i;
                }
            }

            // add to queue
            if (shortestQueueIndex != -1) {
                cozi.get(shortestQueueIndex).addClient(client);
            }
            else cozi.get(0).addClient(client);
        }
    }
}

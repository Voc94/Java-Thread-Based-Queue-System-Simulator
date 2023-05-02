package strategy;

import modele.Client;
import modele.Coada;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Coada> queues;
    private final SelectionPolicy selectionPolicy;
    public Scheduler(int maxNrQueues, int nrClienti,SelectionPolicy selectionPolicy) {
        this.queues = new ArrayList<>(maxNrQueues);
        for (int i = 0; i < maxNrQueues; i++) {
            Coada queue = new Coada();
            queues.add(queue);
            new Thread(queue).start();
        }
        this.selectionPolicy = selectionPolicy;
    }


    public void dispatchTask(Client t) {
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE) {
            Coada minSizeCoada = queues.get(0);
            for (Coada coada : queues) {
                if (coada.getClientiSize() < minSizeCoada.getClientiSize()) {
                    minSizeCoada = coada;
                }
            }
            minSizeCoada.addClient(t);
        } else if (selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
            Coada minWaitingTimeCoada = queues.get(0);
            for (Coada coada : queues) {
                if (coada.getWaitingPeriod().get() < minWaitingTimeCoada.getWaitingPeriod().get()) {
                    minWaitingTimeCoada = coada;
                }
            }
            minWaitingTimeCoada.addClient(t);
        }
    }

    public List<Coada> getQueues() {
        return queues;
    }

}

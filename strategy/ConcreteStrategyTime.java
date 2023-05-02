package strategy;

import modele.Client;
import modele.Coada;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Coada> cozi, Client t) {
            Coada minWaitingTimeCoada = cozi.get(0);
            for (Coada coada : cozi) {
                if (coada.getWaitingPeriod().get() < minWaitingTimeCoada.getWaitingPeriod().get()) {
                    minWaitingTimeCoada = coada;
                }
            }
            minWaitingTimeCoada.addClient(t);
    }
}

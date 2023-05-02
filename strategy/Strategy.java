package strategy;

import modele.Client;
import modele.Coada;

import java.util.List;

public interface Strategy {
    void addTask(List<Coada> cozi, Client t);
}



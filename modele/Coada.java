package modele;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Coada implements Runnable {
    private BlockingQueue<Client> clientQueue;
    private AtomicInteger waitingPeriod;

    public Coada() {
        this.clientQueue = new ArrayBlockingQueue<>(1001);
        this.waitingPeriod = new AtomicInteger(0);
    }

    public List<Client> getClienti() {
        return new ArrayList<>(clientQueue);
    }

    public void addClient(Client newClient) {
        clientQueue.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
    }

    public int getClientiSize() {
        return clientQueue.size();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Client peekClient() {
        return clientQueue.peek();
    }

    public Client pollClient() {
        return clientQueue.poll();
    }



    @Override
    public void run() {
        while (true) {
            Client currentClient = clientQueue.poll();

            int serviceTime = currentClient.getServiceTime();
            if (serviceTime >= 1) {
                currentClient.setServiceTime(serviceTime - 1);
                    clientQueue.add(currentClient);
                    waitingPeriod.decrementAndGet();
            } else {
                    waitingPeriod.decrementAndGet();
            }
        }
    }
}

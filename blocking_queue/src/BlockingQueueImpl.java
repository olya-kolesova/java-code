import java.util.LinkedList;
import java.util.List;

public class BlockingQueueImpl<E> {
    private List<E> queue = new LinkedList<>();
    private int limit = 5;
    public BlockingQueueImpl(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(E e) throws InterruptedException {
        while(queue.size() == limit) {
            wait();
        }
        if (queue.isEmpty()) {
            notifyAll();
        }

        queue.add(e);

    }

    public synchronized E dequeue() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }

        if (queue.size() == limit) {
            notifyAll();
        }

        return queue.removeFirst();
    }

    public int size() {
        return queue.size();
    }


}

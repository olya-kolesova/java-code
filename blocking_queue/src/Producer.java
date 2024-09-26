import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
    private BlockingQueueImpl<Integer> queue;

    public Producer(BlockingQueueImpl<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            Integer number = ThreadLocalRandom.current().nextInt(100);
            queue.enqueue(number);
            System.out.println(Thread.currentThread().getName() + " produced " + number);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}

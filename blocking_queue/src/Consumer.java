public class Consumer implements Runnable {
    private BlockingQueueImpl<Integer> queue;

    public Consumer(BlockingQueueImpl<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            Integer number = queue.dequeue();
            System.out.println(Thread.currentThread().getName() + ": Consumed " + number);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

}

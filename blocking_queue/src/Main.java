public class Main {
    public static void main(String[] args) {
        int limit = 5;
        int producers = 7;
        int consumers = 7;
        BlockingQueueImpl<Integer> queue = new BlockingQueueImpl<>(limit);

        for (int i = 0; i < producers; i++) {
            new Thread(new Producer(queue)).start();
        }

        for (int i = 0; i < consumers; i++) {
            new Thread(new Consumer(queue)).start();
        }

    }
}
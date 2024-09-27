import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class ComplexTask {
    CyclicBarrier barrier;
    List<Integer> partialResults;


    public ComplexTask(CyclicBarrier barrier, List<Integer> partialResults) {
        this.barrier = barrier;
        this.partialResults = partialResults;
    }

    public void execute() {
        int number = ThreadLocalRandom.current().nextInt(10);
        partialResults.add(number);
        System.out.println("Thread:" + Thread.currentThread().getName() + " added number: " + number);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    List<Integer> partialResults = Collections.synchronizedList(new ArrayList<Integer>());

    public void executeTasks(int numberOfTasks) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, new AggregatorTask(partialResults));
        ComplexTask complexTask = new ComplexTask(barrier, partialResults);

        for (int i = 0; i < numberOfTasks; i++) {
            executor.submit(() -> complexTask.execute());
        }
        executor.shutdown();
    }

}

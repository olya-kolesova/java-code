import java.util.List;

public class AggregatorTask implements Runnable {

    List<Integer> partialResults;

    public AggregatorTask(List<Integer> partialResults) {
        this.partialResults = partialResults;
    }

    @Override
    public void run() {
        int sum = partialResults.stream().reduce(0, Integer::sum);
        System.out.printf("Final result: %d\n", sum);
    }
}

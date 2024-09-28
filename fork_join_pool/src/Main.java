import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        FactorialTask task = new FactorialTask(10);
        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(task);

        System.out.printf("Factorial is %d\n", result);
    }
}
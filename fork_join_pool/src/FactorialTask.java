import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FactorialTask extends RecursiveTask<Integer> {
    private int begin = 1;
    private int end;
    private final int THRESHOLD = 4;



    public FactorialTask(int number) {
        this.end = number;
    }

    @Override
    protected Integer compute() {
        if (end - begin > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                .stream().mapToInt(ForkJoinTask::join).reduce(1, (a, b) -> a * b);

        } else {
            return process(begin, end);
        }

    }

    private void setBegin(int begin) {
        this.begin = begin;
    }

    private List<FactorialTask> createSubtasks() {
        List<FactorialTask> subTasks = new ArrayList<>();
        FactorialTask left = new FactorialTask(begin + (end - begin) / 2);
        left.setBegin(begin);
        FactorialTask right = new FactorialTask(end);
        int secondBegin = begin + (end - begin) / 2 + 1;
        right.setBegin(begin + (end - begin) / 2 + 1);
        subTasks.add(left);
        subTasks.add(right);
        return subTasks;
    }


    private int process(int begin, int end) {
        return IntStream.iterate(begin, i -> i + 1).limit(end - begin + 1).reduce(1, (a, b) -> a * b);
    }

}

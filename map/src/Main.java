import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String[] array = {"apple", "orange", "pear", "apple", "orange", "pear"};
        MapCounter mapCounter = new MapCounter();
        Set<Map.Entry<Object, Integer>> entries = mapCounter.count(array).entrySet();
        entries.forEach(System.out::println);
    }

}
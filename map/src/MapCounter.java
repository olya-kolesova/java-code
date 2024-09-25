import java.util.HashMap;
import java.util.Map;

public class MapCounter {

    public Map<Object, Integer> count(Object[] array) {
        Map<Object, Integer> map = new HashMap<>();
        for (Object element : array) {
            if (map.containsKey(element)) {
                map.put(element, map.get(element) + 1);
            } else {
                map.put(element, 1);
            }
        }
        return map;
    }
}

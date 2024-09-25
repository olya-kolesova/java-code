//Напишите метод filter, который принимает на вход массив любого типа, вторым арументом метод должен принимать клас, реализующий интерфейс Filter, в котором один метод - Object apply(Object o).
//
//Метод должен быть реализован так чтобы возращать новый масив, к каждому элементу которого была применена функция apply

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String[] arrayStr = {"1", "2", "3"};
        FilterImpl filterImpl = new FilterImpl();
        Stream.of(filter(arrayStr, filterImpl)).forEach(System.out::println);

    }

    public static Object[] filter(Object[] array, FilterImpl filterImpl) {
        List<Object> objects = new ArrayList<>();
        for (Object object : array) {
            objects.add(filterImpl.apply(object));
        }
        return objects.toArray();
    }

}
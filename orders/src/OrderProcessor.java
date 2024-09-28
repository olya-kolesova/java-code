import java.util.*;

import static java.util.stream.Collectors.*;

public class OrderProcessor {

    public Map<String, List<Order>> groupOrdersByProduct(List<Order> orders) {
        Map<String, List<Order>> ordersMap = orders.stream().collect(groupingBy(Order::getProduct));
        ordersMap.entrySet().stream().forEach(System.out::println);
        return ordersMap;
    }


    public Map<String, Double> getWithCommonPrice(List<Order> orders) {
        Map<String, Double> productsMap = orders.stream()
                .collect(groupingBy(Order::getProduct, summingDouble(Order::getCost)));
        productsMap.entrySet().stream().forEach(System.out::println);
        return productsMap;
    }

    public Map<String, Double> getSortedOrders(List<Order> orders) {
        return getWithCommonPrice(orders).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .peek(System.out::println).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Map<String, Double> getThreeExpensiveProducts(List<Order> orders) {
        Map<String, Double> products = orders.stream().collect(groupingBy(Order::getProduct, averagingDouble(Order::getCost))).entrySet()
            .stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3).peek(System.out::println)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));

        products.entrySet().stream().forEach(System.out::println);
        return products;
    }



    public Map<String, Double> getWithCommonPriceThreeExpensiveProducts(List<Order> orders) {
        record Product(String product, double sum) {}
        Map<String, Product> products = orders.stream().collect(groupingBy(Order::getProduct, collectingAndThen(toList(), list -> {
            String product = list.getFirst().getProduct();
            double sum = list.stream().mapToDouble(Order::getCost).sum();
            return new Product(product, sum);
        })));
        Map<String, Double> result = getThreeExpensiveProducts(orders).keySet().stream().map(products::get).peek(System.out::println).collect(toMap(Product::product, Product::sum));
        return result;
    }


}

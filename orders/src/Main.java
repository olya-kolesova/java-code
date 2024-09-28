import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.groupOrdersByProduct(orders);
        orderProcessor.getWithCommonPrice(orders);
        orderProcessor.getSortedOrders(orders);
        orderProcessor.getThreeExpensiveProducts(orders);
        orderProcessor.getWithCommonPriceThreeExpensiveProducts(orders);
    }
}
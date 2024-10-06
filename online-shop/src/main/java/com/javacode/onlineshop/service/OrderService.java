package com.javacode.onlineshop.service;

import com.javacode.onlineshop.model.Order;
import com.javacode.onlineshop.model.Product;
import com.javacode.onlineshop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }


    public List<Product> checkProducts(List<Product> underCheckProducts) throws IllegalArgumentException {
        List<Long> productIds = underCheckProducts.stream().map(Product::getId).toList();
        List<Product> checkedProducts = productService.checkPresenceAndAmount(productIds);
        if (checkedProducts.isEmpty()) {
            throw new IllegalArgumentException("There are no present products");
        }
        return checkedProducts;
    }

    public double countTotalPrice(List<Product> products) {
        return products.stream().map(Product::getPrice).reduce(0.0, Double::sum);
    }

    @Transactional
    public Order save(Order order) {
        List<Product> products = order.getProducts();
        List<Product> checkedProducts = checkProducts(products);
        productService.decreaseAmount(checkedProducts);
        order.setProducts(checkedProducts);
        order.setTotalPrice(countTotalPrice(checkedProducts));
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.OrderStatus.ORDERED);
        return orderRepository.save(order);
    }

    public Order findById(Long id) throws NoSuchElementException {
        return orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void removeProductAssociation(Product product) {
        List<Long> orderIds = product.getProductOrders().stream().map(Order::getId).toList();
        List<Order> foundOrders = orderRepository.findAllById(orderIds);
        for (Order order : foundOrders) {
            order.removeProduct(product);
        }
        orderRepository.saveAll(foundOrders);
    }

}

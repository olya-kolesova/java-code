package com.javacode.internet_shop.service;

import com.javacode.internet_shop.model.Item;
import com.javacode.internet_shop.model.Order;
import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.repository.ItemRepository;
import com.javacode.internet_shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, UserService userService, ItemService itemService, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public double countPrice(List<Item> checkedItems) throws NoSuchElementException {
        return checkedItems.stream().map(Item::getPrice).reduce(Double::sum).orElseThrow(
            () -> new NoSuchElementException("There is no price in this order"));
    }


    public Order create(long userId, List<Item> items) throws NoSuchElementException {
        User user = userService.findUserById(userId);
        List<Long> itemIds = items.stream().map(Item::getId).toList();
        List<Item> checkedItems = itemService.findAllById(itemIds);
        Order order = new Order();
        order.setUser(user);
        order.setItems(checkedItems);
        order.setCost(countPrice(checkedItems));
        order.setStatus(Order.Status.ORDERED);
        return save(order);
    }

    public Order findById(long orderId) throws NoSuchElementException {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new NoSuchElementException("There is no order with id " + orderId));
    }




}

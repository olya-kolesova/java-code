package com.javacode.internet_shop.service;

import com.javacode.internet_shop.model.Item;
import com.javacode.internet_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAllById(List<Long> ids) {
        return itemRepository.findAllById(ids);
    }


}

package com.javacode.internet_shop.service;

import com.javacode.internet_shop.model.Item;
import com.javacode.internet_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }


}

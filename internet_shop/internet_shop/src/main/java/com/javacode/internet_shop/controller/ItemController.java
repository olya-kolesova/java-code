package com.javacode.internet_shop.controller;


import com.javacode.internet_shop.model.Item;
import com.javacode.internet_shop.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/api/shop/item")
    public ResponseEntity<Object> createNewItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.addItem(item), HttpStatus.CREATED) ;
    }

}

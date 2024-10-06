package com.javacode.onlineshop.controller;

import com.javacode.onlineshop.model.Product;
import com.javacode.onlineshop.repository.ProductRepository;
import com.javacode.onlineshop.service.OrderService;
import com.javacode.onlineshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public ProductController(ProductService productService, ProductRepository productRepository, OrderService orderService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    @PostMapping("/api/shop/product")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/api/shop/product/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }


    @PutMapping("/api/shop/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.update(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/api/shop/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        Product foundProduct = productService.findById(id);
        if (!foundProduct.getProductOrders().isEmpty()) {
            orderService.removeProductAssociation(foundProduct);
        }
        productService.delete(foundProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

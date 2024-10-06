package com.javacode.onlineshop.service;


import com.javacode.onlineshop.model.Order;
import com.javacode.onlineshop.model.Product;
import com.javacode.onlineshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    public List<Product> checkAmount(List<Product> products) {
        return products.stream().filter(x -> x.getQuantityInStock() > 0).toList();
    }

    public List<Product> checkPresenceAndAmount(List<Long> productIds) {
        return checkAmount(findAllById(productIds));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void decreaseAmount(List<Product> products) {
        for (Product product : products) {
            product.setQuantityInStock(product.getQuantityInStock() - 1);
        }
        productRepository.saveAll(products);
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Product update(long id, Product product) {
        Product foundProduct = findById(id);
        foundProduct.setName(product.getName());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setQuantityInStock(foundProduct.getQuantityInStock() + product.getQuantityInStock());
        return productRepository.save(foundProduct);
    }

    public void delete(Product product) throws NoSuchElementException {
        productRepository.delete(product);
    }

}

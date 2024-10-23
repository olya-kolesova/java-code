package com.javacode.postgresql;

import com.javacode.postgresql.repository.CartItemRepository;
import com.javacode.postgresql.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(StartRunner.class);

    ProductRepository productRepository;
    CartItemRepository cartItemRepository;

    public StartRunner(ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("executing initial sql statement");
        System.out.println(productRepository.countPrice());
        logger.info("executing sql statement with inner joins");
        System.out.println(productRepository.countPriceOptimized());
        logger.info("executing sql statement with cart_items table");
        System.out.println(cartItemRepository.countPriceWithCartItems());
        logger.info("executing sql statement changed the order of joins");
        System.out.println(cartItemRepository.countPriceWithCartItemsDifferentOrder());
    }
}

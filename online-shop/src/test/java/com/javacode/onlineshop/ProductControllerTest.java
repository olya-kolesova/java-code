package com.javacode.onlineshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.onlineshop.controller.ProductController;
import com.javacode.onlineshop.model.Product;
import com.javacode.onlineshop.repository.ProductRepository;
import com.javacode.onlineshop.service.OrderService;
import com.javacode.onlineshop.service.ProductService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderService orderService;

    @Autowired
     private ObjectMapper objectMapper;

    Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1);
        product.setName("High chair");
        product.setDescription("High chair for little kids");
        product.setPrice(2500.0);
        product.setQuantityInStock(15);
    }

    @Test
    @Order(1)
    public void createProduct_withProduct_isCreated() throws Exception {
        given(productService.save(any(Product.class))).willReturn(product);

        ResultActions response = mockMvc.perform(post("/api/shop/product")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)));


        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.quantityInStock").value(product.getQuantityInStock()));
    }

    @Test
    @Order(2)
    public void getProducts_returnListOfProductsWithSize2() throws Exception {
        List<Product> products = new ArrayList<>();
        Product anotherProduct = new Product();
        anotherProduct.setName("Train");
        anotherProduct.setDescription("train with the railways");
        anotherProduct.setPrice(3500.0);
        anotherProduct.setQuantityInStock(5);
        products.add(product);
        products.add(anotherProduct);

        given(productService.findAll()).willReturn(products);

        ResultActions response = mockMvc.perform(get("/api/shop/product/list"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(products.size())));
    }

    @Test
    @Order(3)
    public void getProduct_withId_returnGivenProduct() throws Exception {

        given(productService.findById(product.getId())).willReturn(product);

        ResultActions response = mockMvc.perform(get("/api/shop/product/{id}", product.getId()));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.quantityInStock").value(product.getQuantityInStock()));
    }


    @Test
    @Order(4)
    public void updateProduct_withProduct_isUpdated() throws Exception {
        given(productService.findById(product.getId())).willReturn(product);
        product.setName("Blue train");
        product.setDescription("Blue train for little kids with railways");
        given(productService.update(product.getId(), product)).willReturn(product);

        ResultActions response = mockMvc.perform(put("/api/shop/product/{id}", product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)));


        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()));

    }

    @Test
    @Order(5)
    public void deleteProduct_withId1_isOk() throws Exception {
        given(productService.findById(product.getId())).willReturn(product);
        willDoNothing().given(productService).delete(product);
        ResultActions response = mockMvc.perform(delete("/api/shop/product/{id}", product.getId()));
        response.andExpect(status().isOk());

    }






}

package org.example.springjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.springjpa.dto.ProductDto;
import org.example.springjpa.mapper.impl.ProductMapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;
import org.example.springjpa.repository.ValueRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;

@WebMvcTest({ProductController.class, ProductMapper.class})
public class ProductControllerTest {
    @MockitoBean
    ProductRepository productRepository;

    @MockitoBean
    CategoryRepository categoryRepository;

    @MockitoBean
    ValueRepository valueRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @SneakyThrows
    void findAllEpicSuccess() {
        Category category = new Category();
        category.setId(1);
        category.setName("Категория");

        Product firstProduct = new Product();
        firstProduct.setId(1);
        firstProduct.setName("Продукт-1");
        firstProduct.setPrice(100.0);
        firstProduct.setCategory(category);

        Product secondProduct = new Product();
        secondProduct.setId(2);
        secondProduct.setName("Продукт-2");
        secondProduct.setPrice(95.0);
        secondProduct.setCategory(category);

        int expectedSize = 2;


        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(firstProduct, secondProduct));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedSize))
                .andExpect(jsonPath("$[0].id").value(firstProduct.getId()))
                .andExpect(jsonPath("$[0].name").value(firstProduct.getName()))
                .andExpect(jsonPath("$[0].price").value(firstProduct.getPrice()))
                .andExpect(jsonPath("$[0].category").value(firstProduct.getCategory().getName()))
                .andExpect(jsonPath("$[1].id").value(secondProduct.getId()))
                .andExpect(jsonPath("$[1].name").value(secondProduct.getName()))
                .andExpect(jsonPath("$[1].price").value(secondProduct.getPrice()))
                .andExpect(jsonPath("$[1].category").value(secondProduct.getCategory().getName()));
    }

    @Test
    @SneakyThrows
    void findByIdEpicSuccess() {
        Category category = new Category();
        category.setId(1);
        category.setName("Категория");

        Product product = new Product();
        product.setId(1);
        product.setName("Продукт");
        product.setPrice(100.0);
        product.setCategory(category);


        Mockito.when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));


        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.category").value(product.getCategory().getName()))
                .andExpect(jsonPath("$.options").isEmpty());
    }

    @Test
    @SneakyThrows
    void findByIdEpicFail() {
        int wrongId = 777;

        Mockito.when(productRepository.findById(wrongId))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/products/" + wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createEpicSuccess() {
        Category category = new Category();
        category.setId(1);
        category.setName("Категория");

        ProductDto product = new ProductDto();
        product.setName("Продукт");
        product.setPrice(100.0);



        Mockito.when(categoryRepository.findById(category.getId()))
                        .thenReturn(Optional.of(category));

        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocationOnMock -> {
                    Product returnProduct = invocationOnMock.getArgument(0, Product.class);
                    returnProduct.setId(1);
                    return returnProduct;
                });


        String productJson = objectMapper.writeValueAsString(product);
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
                        .param("categoryId", String.valueOf(category.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.category").value(category.getName()));
    }

    @Test
    @SneakyThrows
    void createEpicFail() {

    }
}

package org.example.springjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.springjpa.dto.CategoryCreateDto;
import org.example.springjpa.dto.CategoryDto;
import org.example.springjpa.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Intel Core I9 9900"))
                .andExpect(jsonPath("$[0].price").value(249990.0))
                .andExpect(jsonPath("$[0].category").value("Процессоры"))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].name").value("AMD RYZEN"))
                .andExpect(jsonPath("$[4].price").value(499990.0))
                .andExpect(jsonPath("$[4].category").value("Процессоры"));

    }

    @Test
    @SneakyThrows
    void findById() {
        int productId = 1;

        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Intel Core I9 9900"))
                .andExpect(jsonPath("$.price").value(249990.0))
                .andExpect(jsonPath("$.category").value("Процессоры"));
    }

    @Test
    @SneakyThrows
    void createTestSuccess() {
        //Создание категории
        CategoryCreateDto categoryDto = new CategoryCreateDto();
        categoryDto.setName("Категория");

        String categoryJson = objectMapper.writeValueAsString(categoryDto);

        String categoryAsString = mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(categoryDto.getName()))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);


        CategoryDto mappedCategoryDto = objectMapper.readValue(categoryAsString, CategoryDto.class);
        String expectedCategoryId = String.valueOf(mappedCategoryDto.getId());
        String expectedCategoryName = mappedCategoryDto.getName();


        // Создание товара
        ProductDto productDto = new ProductDto();
        productDto.setName("Продукт");
        productDto.setPrice(999.0);

        String productJson = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
                        .param("categoryId", expectedCategoryId)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value(expectedCategoryName));

    }

    @Test
    @SneakyThrows
    void createTestInvalidCategory() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Продукт");
        productDto.setPrice(999.0);

        String json = objectMapper.writeValueAsString(productDto);
        String invalidCategoryId = "100000";

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("categoryId", invalidCategoryId)
                ).andExpect(status().isNotFound());
    }
}

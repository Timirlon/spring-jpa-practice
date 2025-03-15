package org.example.springjpa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.springjpa.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
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
        ProductDto productDto = new ProductDto();
        productDto.setName("Продукт");
        productDto.setPrice(999.0);

        String json = objectMapper.writeValueAsString(productDto);
        String categoryId = "1";

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("categoryId", categoryId)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value("Процессоры"));

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

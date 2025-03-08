package org.example.springjpa.service;

import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void shouldThrowExceptionIfCategoryNotFound() {
        String expectedMessage = "Категория не найдена";


        Mockito.when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Product product = new Product();


        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.create(product, 1));
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void shouldSave() {
        Category category = new Category();
        category.setId(1);
        category.setName("Процессоры");


        Mockito.when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        Product product = new Product();
        product.setName("Intel I9");
        product.setPrice(300.0);

        Product savedProduct = productService.create(product, category.getId());

        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getCategory().getId(), savedProduct.getCategory().getId());
        assertEquals(product.getCategory().getName(), savedProduct.getCategory().getName());
    }
}

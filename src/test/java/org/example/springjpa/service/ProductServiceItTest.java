package org.example.springjpa.service;

import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureTestDatabase
public class ProductServiceItTest {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Test
    void updateProductPrice() {
        Category category = new Category();
        category.setName("Мебель");
        categoryService.create(category);

        Product initialProduct = new Product();
        initialProduct.setName("Диван");
        initialProduct.setPrice(990.0);
        productService.create(initialProduct, category.getId());


        Product updateProduct = new Product();
        updateProduct.setPrice(919.0);

        Product returnProduct = productService.update(initialProduct.getId(), updateProduct);


        Assertions.assertEquals(updateProduct.getPrice(), returnProduct.getPrice());
        Assertions.assertEquals(initialProduct.getName(), returnProduct.getName());
        Assertions.assertEquals(initialProduct.getCategory().getId(),
                returnProduct.getCategory().getId());
    }

    @Test
    void updateProductName() {
        Category category = new Category();
        category.setName("Бытовая техника");
        categoryService.create(category);

        Product initialProduct = new Product();
        initialProduct.setName("Посудомойка");
        initialProduct.setPrice(990.0);
        productService.create(initialProduct, category.getId());


        Product updateProduct = new Product();
        updateProduct.setName("Да");

        Product returnProduct = productService.update(initialProduct.getId(), updateProduct);


        Assertions.assertEquals(updateProduct.getName(), returnProduct.getName());
        Assertions.assertEquals(initialProduct.getPrice(), returnProduct.getPrice());
        Assertions.assertEquals(initialProduct.getCategory().getId(),
                returnProduct.getCategory().getId());
    }

    @Test
    void updateNonExistingProduct() {
        Category category = new Category();
        category.setName("Нет");
        categoryService.create(category);

        Product initialProduct = new Product();
        initialProduct.setName("Да");
        initialProduct.setPrice(990.0);
        productService.create(initialProduct, category.getId());


        Product updateProduct = new Product();
        updateProduct.setName("Не повезло");
        updateProduct.setPrice(100.0);

        Assertions.assertThrows(NoSuchElementException.class, () -> productService.update(initialProduct.getId() + 1, updateProduct));
    }
}

package org.example.springjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product create(Product product, int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public Product update(int productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId).orElseThrow();
        if (updatedProduct.getName() != null && !updatedProduct.getName().isBlank()) {
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        productRepository.save(existingProduct);
        return existingProduct;
    }
}


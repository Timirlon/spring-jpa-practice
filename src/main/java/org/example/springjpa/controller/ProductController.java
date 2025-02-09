package org.example.springjpa.controller;


import lombok.RequiredArgsConstructor;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Product create(@RequestParam int categoryId, @RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }

    @GetMapping("/in-price-range")
    public List<Product> findAllInPriceRange(@RequestParam double from, @RequestParam double to) {
        return productRepository.findALlByPriceBetween(from, to);
    }

    @GetMapping("/containing")
    public List<Product> findAllByNameContaining(@RequestParam String subStr) {
        return productRepository.findAllByNameContainingIgnoreCase(subStr);
    }

    @GetMapping("/top-price")
    public Product findMostExpensiveProduct() {
        return productRepository.findTopByOrderByPriceDesc().orElseThrow();
    }
}

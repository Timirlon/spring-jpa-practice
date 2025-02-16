package org.example.springjpa.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjpa.dto.ProductDto;
import org.example.springjpa.dto.ProductFullDto;
import org.example.springjpa.mapper.Mapper;
import org.example.springjpa.mapper.impl.ProductMapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;
import org.example.springjpa.repository.ValueRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ValueRepository valueRepository;

    ProductMapper productMapper;


    @GetMapping
    public List<ProductDto> findAll() {
        List<Product> prodList = productRepository.findAll();
        return productMapper.toDto(prodList);
    }

    @GetMapping("/{id}")
    public ProductFullDto findById(@PathVariable int id) {
        Product prod = productRepository.findById(id).orElseThrow();
        prod.setValues(valueRepository.findByProductId(id));

        return productMapper.toFullDto(prod);
    }

    @PostMapping
    public Product create(@RequestParam int categoryId, @RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
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

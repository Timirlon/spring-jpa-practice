package org.example.springjpa.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjpa.dto.ProductDto;
import org.example.springjpa.dto.ProductFullDto;
import org.example.springjpa.mapper.impl.ProductMapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Product;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.ProductRepository;
import org.example.springjpa.repository.ValueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    public Optional<ProductFullDto> findById(@PathVariable int id) {
        Product prod = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        prod.setValues(valueRepository.findByProductId(id));

        return Optional.of(productMapper.toFullDto(prod));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestParam int categoryId, @RequestBody ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Product product = productMapper.fromDto(productDto);
        product.setCategory(category);

        return productMapper.toDto(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());

        return productMapper.toDto(productRepository.save(existingProduct));
    }

    @GetMapping("/in-price-range")
    public List<ProductDto> findAllInPriceRange(@RequestParam double from, @RequestParam double to) {
        return productMapper.toDto(productRepository.findALlByPriceBetween(from, to));
    }

    @GetMapping("/containing")
    public List<ProductDto> findAllByNameContaining(@RequestParam String subStr) {
        return productMapper.toDto(productRepository.findAllByNameContainingIgnoreCase(subStr));
    }

    @GetMapping("/top-price")
    public ProductDto findMostExpensiveProduct() {
        return productMapper.toDto(productRepository.findTopByOrderByPriceDesc().orElseThrow());
    }
}

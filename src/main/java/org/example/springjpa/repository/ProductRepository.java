package org.example.springjpa.repository;

import org.example.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findALlByPriceBetween(double from, double to);

    List<Product> findAllByNameContainingIgnoreCase(String subStr);

    Optional<Product> findTopByOrderByPriceDesc();
}

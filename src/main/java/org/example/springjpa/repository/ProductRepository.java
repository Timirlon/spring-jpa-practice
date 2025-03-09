package org.example.springjpa.repository;

import org.example.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findALlByPriceBetween(double from, double to);

    List<Product> findAllByNameContainingIgnoreCase(String subStr);

    Optional<Product> findTopByOrderByPriceDesc();

    @Query("""
    SELECT p, v FROM Product p
        JOIN p.values v
            WHERE v.name = :#{$name}
    """)
    List<Product> findAllByValuesHavingName(String name);


    /*
    @Query("""
    SELECT o, v, p FROM Value v
    RIGHT JOIN v.option o
    LEFT JOIN v.product p
    WHERE p.id = :#{#id}""")
    Optional<Product> findById(int id);
    */
}

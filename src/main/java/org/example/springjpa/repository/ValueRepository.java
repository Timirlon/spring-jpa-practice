package org.example.springjpa.repository;

import org.example.springjpa.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Integer> {
    public List<Value> findByProductId(int productId);
}

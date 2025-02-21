package org.example.springjpa.repository;

import org.example.springjpa.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Integer> {
    @Query("""
SELECT v FROM Value v
JOIN FETCH v.option o
JOIN FETCH o.category
where v.product.id=:productId""")
    public List<Value> findByProductId(int productId);
}

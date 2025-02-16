package org.example.springjpa.repository;

import org.example.springjpa.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    public List<Option> findByCategoryId(int categoryId);
}

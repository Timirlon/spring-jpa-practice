package org.example.springjpa.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjpa.dto.CategoryCreateDto;
import org.example.springjpa.mapper.impl.CategoryMapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.OptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    CategoryRepository categoryRepository;
    OptionRepository optionRepository;
    CategoryMapper categoryMapper;

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Category create(@RequestBody CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.fromDto(categoryCreateDto);

        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow();
        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }
}

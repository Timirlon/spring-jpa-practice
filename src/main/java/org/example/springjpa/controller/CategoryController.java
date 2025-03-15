package org.example.springjpa.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.springjpa.dto.CategoryCreateDto;
import org.example.springjpa.dto.CategoryDto;
import org.example.springjpa.mapper.impl.CategoryMapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.OptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable int id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.fromDto(categoryCreateDto);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable int id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow();
        existingCategory.setName(category.getName());

        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }
}

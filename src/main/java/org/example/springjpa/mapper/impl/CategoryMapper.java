package org.example.springjpa.mapper.impl;

import org.example.springjpa.dto.CategoryCreateDto;
import org.example.springjpa.dto.CategoryDto;
import org.example.springjpa.mapper.Mapper;
import org.example.springjpa.model.Category;
import org.example.springjpa.model.Option;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.example.springjpa.dto.CategoryDto.OptionForCategoryDto;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    public List<CategoryDto> toDto(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setOptions(toOptionDto(category.getOptions()));
        return categoryDto;
    }

    private List<OptionForCategoryDto> toOptionDto(List<Option> options) {
        return options.stream()
                .map(this::toOptionDto)
                .toList();
    }

    private OptionForCategoryDto toOptionDto(Option option) {
        OptionForCategoryDto optionDto = new OptionForCategoryDto();
        optionDto.setId(option.getId());
        optionDto.setName(option.getName());
        return optionDto;
    }

    public Category fromDto(CategoryCreateDto categoryCreateDto) {
        Category category = new Category();

        category.setName(categoryCreateDto.getName());

        if (categoryCreateDto.getOptions() != null) {
            List<Option> options = Arrays.stream(categoryCreateDto.getOptions())
                    .map(optionStr -> {
                        Option option = new Option();
                        option.setName(optionStr);
                        option.setCategory(category);


                        return option;
                    })
                    .toList();


            category.setOptions(options);
        }


        return category;
    }
}


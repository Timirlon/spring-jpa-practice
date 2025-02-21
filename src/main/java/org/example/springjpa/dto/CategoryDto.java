package org.example.springjpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int id;
    private String name;
    private List<OptionForCategoryDto> options;

    @Data
    public static class OptionForCategoryDto {
        private int id;
        private String name;
    }
}


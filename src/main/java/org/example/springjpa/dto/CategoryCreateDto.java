package org.example.springjpa.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.springjpa.model.Category;

@Getter
@Setter

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryCreateDto {
    String name;
    String[] options;
}

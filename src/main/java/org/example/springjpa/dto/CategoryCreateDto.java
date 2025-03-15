package org.example.springjpa.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateDto {
    String name;
    String[] options;
}

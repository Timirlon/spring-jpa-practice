package org.example.springjpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class ProductFullDto extends ProductDto {


    final Map<String, String> options = new HashMap<>();

    public void addOption(String str1, String str2) {
        options.put(str1, str2);
    }
}

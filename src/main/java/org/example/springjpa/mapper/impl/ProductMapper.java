package org.example.springjpa.mapper.impl;

import org.example.springjpa.dto.ProductDto;
import org.example.springjpa.dto.ProductFullDto;
import org.example.springjpa.mapper.Mapper;
import org.example.springjpa.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper implements Mapper<Product, ProductDto> {
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getName());


        return productDto;
    }

    public List<ProductDto> toDto(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    public ProductFullDto toFullDto(Product product) {
        ProductFullDto fullDto = new ProductFullDto();

        fullDto.setId(product.getId());
        fullDto.setName(product.getName());
        fullDto.setPrice(product.getPrice());
        fullDto.setCategory(product.getCategory().getName());

        product.getValues().forEach(value -> {
            String key = value.getOption().getName();
            String val = value.getName();

            fullDto.addOption(key, val);
        });

        return fullDto;
    }
}

package org.example.springjpa.service;

import org.example.springjpa.model.Category;
import org.example.springjpa.model.Option;
import org.example.springjpa.model.Product;
import org.example.springjpa.model.Value;
import org.example.springjpa.repository.CategoryRepository;
import org.example.springjpa.repository.OptionRepository;
import org.example.springjpa.repository.ProductRepository;
import org.example.springjpa.repository.ValueRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    ValueRepository valueRepository;


    @Test
    void saveProduct() {
        final int expectedNumberOfValues = 2;

        // Категория
        Category smartphoneCategory = new Category();
        smartphoneCategory.setName("Smartphones");
        categoryRepository.save(smartphoneCategory);


        // Характеристика
        Option colorOption = new Option();
        colorOption.setName("Color");
        colorOption.setCategory(smartphoneCategory);
        optionRepository.save(colorOption);

        Option storageOption = new Option();
        storageOption.setName("Storage");
        storageOption.setCategory(smartphoneCategory);
        optionRepository.save(storageOption);


        // Товар
        Product iPhoneProduct = new Product();
        iPhoneProduct.setName("iPhone 15");
        iPhoneProduct.setPrice(1489.0);
        iPhoneProduct.setCategory(smartphoneCategory);
        productRepository.save(iPhoneProduct);


        // Значения
        Value blackColorValue = new Value();
        blackColorValue.setName("Black");
        blackColorValue.setOption(colorOption);
        blackColorValue.setProduct(iPhoneProduct);
        valueRepository.save(blackColorValue);


        Value storageValue = new Value();
        storageValue.setName("128 GB");
        storageValue.setOption(storageOption);
        storageValue.setProduct(iPhoneProduct);
        valueRepository.save(storageValue);



        assertEquals(expectedNumberOfValues, iPhoneProduct.getValues().size());
    }

    @Test
    void checkMethods() {
        Category smartphoneCategory = new Category();
        smartphoneCategory.setName("Smartphones");
        categoryRepository.save(smartphoneCategory);

        Category laptopCategory = new Category();
        smartphoneCategory.setName("Laptops");
        categoryRepository.save(smartphoneCategory);


        Option smartphoneManufacturerOption = new Option();
        smartphoneManufacturerOption.setName("Manufacturer");
        smartphoneManufacturerOption.setCategory(smartphoneCategory);
        optionRepository.save(smartphoneManufacturerOption);

        Option smartphoneStorageOption = new Option();
        smartphoneStorageOption.setName("Storage");
        smartphoneStorageOption.setCategory(smartphoneCategory);
        optionRepository.save(smartphoneStorageOption);

        Option laptopManufacturerOption = new Option();
        laptopManufacturerOption.setName("Manufacturer");
        laptopManufacturerOption.setCategory(laptopCategory);
        optionRepository.save(laptopManufacturerOption);

        Option laptopStorageOption = new Option();
        laptopStorageOption.setName("Storage");
        laptopStorageOption.setCategory(laptopCategory);
        optionRepository.save(laptopStorageOption);


        Product iPhone = new Product();
        iPhone.setName("iPhone 12");
        iPhone.setPrice(1990.0);
        iPhone.setCategory(smartphoneCategory);
        productRepository.save(iPhone);

        Product macBook = new Product();
        macBook.setName("MacBook Pro 14");
        macBook.setPrice(2440.0);
        macBook.setCategory(laptopCategory);


        Value iPhoneManufacturer = new Value();
        iPhoneManufacturer.setName("Apple");
        iPhoneManufacturer.setProduct(iPhone);
        iPhoneManufacturer.setOption(smartphoneManufacturerOption);
        valueRepository.save(iPhoneManufacturer);

        Value iPhoneStorage = new Value();
        iPhoneStorage.setName("128 GB");
        iPhoneStorage.setProduct(iPhone);
        iPhoneStorage.setOption(smartphoneStorageOption);
        valueRepository.save(iPhoneStorage);

        Value macBookManufacturer = new Value();
        macBookManufacturer.setName("Apple");
        macBookManufacturer.setProduct(macBook);
        macBookManufacturer.setOption(laptopManufacturerOption);
        valueRepository.save(macBookManufacturer);

        Value macBookStorage = new Value();
        macBookStorage.setName("512 GB");
        macBookStorage.setProduct(macBook);
        macBookStorage.setOption(laptopStorageOption);
        valueRepository.save(macBookStorage);


        List<Product> productsWithAppleValue = productRepository.findAllByValuesHavingName("Apple");

        final int expectedSize = 2;
        assertEquals(expectedSize, productsWithAppleValue.size());

    }
}

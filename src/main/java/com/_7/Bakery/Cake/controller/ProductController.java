package com._7.Bakery.Cake.controller;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com._7.Bakery.Cake.FileStorage;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {
    @GetMapping("/api/products")
    public List<Map<String, Object>> getProducts() {
        return FileStorage.readFromFile(
            FileStorage.getProductsFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
    }

    @PostMapping("/api/products")
    public Map<String, Object> addProduct(@RequestBody Map<String, Object> product) {
        List<Map<String, Object>> products = FileStorage.readFromFile(
            FileStorage.getProductsFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        products.add(product);
        FileStorage.writeToFile(FileStorage.getProductsFile(), products);
        return product;
    }
} 
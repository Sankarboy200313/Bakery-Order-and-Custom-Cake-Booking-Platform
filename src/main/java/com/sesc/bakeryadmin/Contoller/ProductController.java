package com.sesc.bakeryadmin.Contoller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    private final String PRODUCT_PATH = "src/main/resources/data/product.txt";

    @GetMapping
    public List<Map<String, String>> getAllProducts() throws IOException {
        List<Map<String, String>> products = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(PRODUCT_PATH));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                Map<String, String> p = new HashMap<>();
                p.put("id", parts[0]);
                p.put("name", parts[1]);
                p.put("price", parts[2]);
                p.put("category", parts[3]);
                products.add(p);
            }
        }
        return products;
    }

    @PostMapping
    public String addProduct(@RequestBody Map<String, String> product) throws IOException {
        String line = String.format("%s,%s,%s,%s\n",
                product.get("id"),
                product.get("name"),
                product.get("price"),
                product.get("category"));
        Files.write(Paths.get(PRODUCT_PATH), line.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        return "Product added";
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable String id) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PRODUCT_PATH));
        List<String> updated = lines.stream().filter(l -> !l.startsWith(id + ",")).toList();
        Files.write(Paths.get(PRODUCT_PATH), updated);
        return "Product removed";
    }
}


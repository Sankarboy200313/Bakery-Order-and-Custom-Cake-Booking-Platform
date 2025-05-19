package com._7.Bakery.Cake.controller;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com._7.Bakery.Cake.FileStorage;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/custom-cakes")
public class CustomCakeController {
    @GetMapping
    public List<Map<String, Object>> getAllCustomCakes() {
        return FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
    }

    @PostMapping
    public Map<String, Object> createCustomCake(@RequestBody Map<String, Object> customCake) {
        customCake.put("id", UUID.randomUUID().toString());
        customCake.put("orderDate", new Date().toString());
        customCake.put("type", "custom");
        customCake.put("paymentStatus", "Pending");

        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        orders.add(customCake);
        FileStorage.writeToFile(FileStorage.getOrdersFile(), orders);
        return customCake;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getCustomCakeById(@PathVariable String id) {
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(id) && "custom".equals(order.get("type"))) {
                return order;
            }
        }
        throw new RuntimeException("Custom cake not found");
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateCustomCake(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(id) && "custom".equals(order.get("type"))) {
                order.putAll(updates);
                FileStorage.writeToFile(FileStorage.getOrdersFile(), orders);
                return order;
            }
        }
        throw new RuntimeException("Custom cake not found");
    }
} 
package com._7.Bakery.Cake.controller;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com._7.Bakery.Cake.FileStorage;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {
    @GetMapping("/api/orders")
    public List<Map<String, Object>> getOrders() {
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        orders.sort((a, b) -> ((String)b.get("orderDate")).compareTo((String)a.get("orderDate")));
        return orders;
    }

    @PostMapping("/api/orders")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> order) {
        order.put("id", UUID.randomUUID().toString());
        order.put("orderDate", new Date().toString());
        order.put("paymentStatus", "Pending");
        
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        orders.add(order);
        FileStorage.writeToFile(FileStorage.getOrdersFile(), orders);
        return order;
    }

    @PutMapping("/api/orders/{id}")
    public Map<String, Object> updateOrder(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(id)) {
                order.putAll(updates);
                FileStorage.writeToFile(FileStorage.getOrdersFile(), orders);
                return order;
            }
        }
        throw new RuntimeException("Order not found");
    }
} 
package com._7.Bakery.Cake.controller;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com._7.Bakery.Cake.FileStorage;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class PaymentController {
    @PostMapping("/api/payments")
    public Map<String, Object> processPayment(@RequestBody Map<String, Object> payment) {
        String orderId = (String) payment.get("orderId");
        List<Map<String, Object>> orders = FileStorage.readFromFile(
            FileStorage.getOrdersFile(),
            new TypeReference<List<Map<String, Object>>>() {}
        );
        
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(orderId)) {
                order.put("paymentStatus", "Paid");
                order.put("paymentDate", new Date().toString());
                order.put("paymentAmount", payment.get("amount"));
                FileStorage.writeToFile(FileStorage.getOrdersFile(), orders);
                return Collections.singletonMap("message", "Payment processed successfully");
            }
        }
        throw new RuntimeException("Order not found");
    }
} 
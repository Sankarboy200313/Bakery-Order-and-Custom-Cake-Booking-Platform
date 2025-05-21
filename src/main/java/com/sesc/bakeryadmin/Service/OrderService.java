package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.Order;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class OrderService {

    private static final String FILE_PATH = "src/main/resources/data/orders.txt";

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    try {
                        Long orderId = Long.parseLong(parts[0].trim());
                        String customer = parts[1].trim();
                        String date = parts[2].trim();
                        Double total = Double.parseDouble(parts[3].trim());
                        String status = parts[4].trim();

                        Order order = new Order(orderId, customer, date, total, status);
                        orders.add(order);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

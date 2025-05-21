package com.bakery.util;

import com.bakery.model.Order;
import com.bakery.model.OrderQueue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String FILE_PATH = "C:\\Users\\M S I\\OneDrive - Sri Lanka Institute of Information Technology\\Desktop\\OOP1\\src\\main\\resources\\orders.txt";

    // Save orders as readable text
    public static void saveOrders(OrderQueue orderQueue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Order order : orderQueue.getAllOrders()) {
                // Write each order as a line with fields separated by |
                writer.write(String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s\n",
                        order.getId(),
                        order.getCustomerName(),
                        order.getEmail(),
                        order.getPhone(),
                        order.getProduct(),
                        order.getSpecialRequests(),
                        order.getDeliveryDate(),
                        order.getDeliveryTime(),
                        order.getAddress(),
                        order.getStatus(),
                        order.getOrderDate()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load orders from readable text
    public static OrderQueue loadOrders() {
        OrderQueue orderQueue = new OrderQueue();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Skip empty lines
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split("\\|");
                    if (parts.length >= 11) {
                        Order order = new Order(parts[0]); // Using a new constructor we'll create
                        order.setCustomerName(parts[1]);
                        order.setEmail(parts[2]);
                        order.setPhone(parts[3]);
                        order.setProduct(parts[4]);
                        order.setSpecialRequests(parts[5]);
                        order.setDeliveryDate(parts[6]);
                        order.setDeliveryTime(parts[7]);
                        order.setAddress(parts[8]);
                        order.setStatus(parts[9]);
                        // Set order date from the saved value
                        order.setOrderDate(parts[10]);

                        orderQueue.addOrder(order);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return orderQueue;
    }
}
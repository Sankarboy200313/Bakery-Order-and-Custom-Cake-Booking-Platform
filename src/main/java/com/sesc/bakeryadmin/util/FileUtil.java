package com.sesc.bakeryadmin.util;

import com.sesc.bakeryadmin.Model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<Order> readOrders(String filename) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long orderId = Long.parseLong(parts[0]);
                    String customer = parts[1];
                    String date = parts[2];
                    Double total = Double.parseDouble(parts[3]);
                    String status = parts[4];
                    orders.add(new Order(orderId, customer, date, total, status));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<String> readUsers(String filename) {
        return readLines(filename);
    }

    public static List<String> readComments(String filename) {
        return readLines(filename);
    }

    private static List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}


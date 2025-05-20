package com.sesc.bakeryadmin.Service;

import com.sesc.bakeryadmin.Model.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String PRODUCT_PATH = "products.txt";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                products.add(new Product(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_PATH, true))) {
            writer.write(product.getName() + "," + product.getQuantity());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(String name) {
        List<Product> updatedList = new ArrayList<>();
        for (Product p : getAllProducts()) {
            if (!String.valueOf(p.getName()).equalsIgnoreCase(name)) { {
                updatedList.add(p);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_PATH))) {
            for (Product p1 : updatedList) {
                writer.write(p.getName() + "," + p.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}}
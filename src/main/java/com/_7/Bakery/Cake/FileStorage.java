package com._7.Bakery.Cake;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

public class FileStorage {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATA_DIR = "data";
    private static final String PRODUCTS_FILE = "products.json";
    private static final String ORDERS_FILE = "orders.json";

    // Read from JSON file
    public static <T> List<T> readFromFile(String filename, TypeReference<List<T>> typeRef) {
        try {
            File file = ResourceUtils.getFile("classpath:" + DATA_DIR + "/" + filename);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, typeRef);
        } catch (IOException e) {
            // If file doesn't exist in classpath, try to read from external location
            try {
                File file = new File(DATA_DIR + "/" + filename);
                if (!file.exists()) {
                    return new ArrayList<>();
                }
                return mapper.readValue(file, typeRef);
            } catch (IOException ex) {
                ex.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    // Write to JSON file
    public static <T> void writeToFile(String filename, List<T> data) {
        try {
            File file = new File(DATA_DIR + "/" + filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            mapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get products file path
    public static String getProductsFile() {
        return PRODUCTS_FILE;
    }

    // Get orders file path
    public static String getOrdersFile() {
        return ORDERS_FILE;
    }
} 
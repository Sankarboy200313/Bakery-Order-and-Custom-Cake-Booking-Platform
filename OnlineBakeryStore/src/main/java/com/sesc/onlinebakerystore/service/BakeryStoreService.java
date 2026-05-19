package com.sesc.onlinebakerystore.service;

import com.sesc.onlinebakerystore.model.BakeryOrder;
import com.sesc.onlinebakerystore.model.BakeryProduct;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BakeryStoreService {
    private static final String PRODUCTS_FILE = "bakery-products.txt";
    private static final String ORDERS_FILE = "bakery-orders.txt";

    @Value("${bakery.store.data-dir:${user.home}/.online-bakery-store}")
    private String dataDirectory;

    @PostConstruct
    public void initializeStorage() throws IOException {
        Files.createDirectories(getDataDir());
        if (Files.notExists(getProductsFile())) {
            seedProducts();
        }
        if (Files.notExists(getOrdersFile())) {
            seedOrders();
        }
    }

    public synchronized List<BakeryProduct> getProducts() throws IOException {
        return loadProducts();
    }

    public synchronized List<BakeryProduct> getFeaturedProducts() throws IOException {
        return loadProducts().stream().filter(BakeryProduct::isFeatured).toList();
    }

    public synchronized BakeryProduct findProductById(int id) throws IOException {
        return loadProducts().stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    public synchronized void saveProduct(BakeryProduct product) throws IOException {
        List<BakeryProduct> products = loadProducts();
        if (product.getId() <= 0) {
            product.setId(nextProductId(products));
            products.add(product);
        } else {
            boolean replaced = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == product.getId()) {
                    products.set(i, product);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                products.add(product);
            }
        }
        writeProducts(products);
    }

    public synchronized void deleteProduct(int id) throws IOException {
        List<BakeryProduct> products = loadProducts();
        products.removeIf(product -> product.getId() == id);
        writeProducts(products);
    }

    public synchronized List<BakeryOrder> getOrders() throws IOException {
        return loadOrders();
    }

    public synchronized BakeryOrder findOrderById(int id) throws IOException {
        return loadOrders().stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }

    public synchronized void saveOrder(BakeryOrder order) throws IOException {
        List<BakeryOrder> orders = loadOrders();
        if (order.getId() <= 0) {
            order.setId(nextOrderId(orders));
            orders.add(order);
        } else {
            boolean replaced = false;
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getId() == order.getId()) {
                    orders.set(i, order);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                orders.add(order);
            }
        }
        writeOrders(orders);
    }

    public synchronized void updateOrderStatus(int id, String status) throws IOException {
        List<BakeryOrder> orders = loadOrders();
        for (BakeryOrder order : orders) {
            if (order.getId() == id) {
                order.setStatus(status);
                writeOrders(orders);
                return;
            }
        }
        throw new IOException("Order not found");
    }

    public synchronized void deleteOrder(int id) throws IOException {
        List<BakeryOrder> orders = loadOrders();
        orders.removeIf(order -> order.getId() == id);
        writeOrders(orders);
    }

    public synchronized long countOpenOrders() throws IOException {
        return loadOrders().stream().filter(order -> !"Delivered".equalsIgnoreCase(order.getStatus())).count();
    }

    public synchronized double totalRevenue() throws IOException {
        return loadOrders().stream().mapToDouble(BakeryOrder::getTotalPrice).sum();
    }

    private Path getDataDir() {
        return Paths.get(dataDirectory);
    }

    private Path getProductsFile() {
        return getDataDir().resolve(PRODUCTS_FILE);
    }

    private Path getOrdersFile() {
        return getDataDir().resolve(ORDERS_FILE);
    }

    private void seedProducts() throws IOException {
        List<BakeryProduct> seed = new ArrayList<>();
        seed.add(new BakeryProduct(1, "Chocolate Celebration Cake", "Custom Cakes", 34.99, 18, "Rich sponge cake layered with Belgian chocolate cream.", "/Image/chocolate-cake..jpg", true));
        seed.add(new BakeryProduct(2, "Butter Croissants", "Pastries", 7.49, 40, "Flaky pastry with a golden, buttery finish.", "/Image/pastries-display.jpg", true));
        seed.add(new BakeryProduct(3, "Signature Bread Box", "Bread", 12.50, 22, "Fresh daily breads with sourdough and multigrain selection.", "/Image/bakery-interior.jpg", false));
        seed.add(new BakeryProduct(4, "Event Dessert Table", "Catering", 89.00, 8, "Curated dessert spreads for parties and corporate events.", "/Image/buffet-table.jpg", true));
        writeProducts(seed);
    }

    private void seedOrders() throws IOException {
        List<BakeryOrder> seed = new ArrayList<>();
        seed.add(new BakeryOrder(1, "Ava", "Chocolate Celebration Cake", 1, 34.99, "0771234567", "2026-05-21", "Processing", "Add blue ribbon"));
        seed.add(new BakeryOrder(2, "Liam", "Butter Croissants", 3, 7.49, "0779876543", "2026-05-22", "Preparing", "Morning delivery"));
        writeOrders(seed);
    }

    private List<BakeryProduct> loadProducts() throws IOException {
        Files.createDirectories(getDataDir());
        if (Files.notExists(getProductsFile())) {
            seedProducts();
        }
        List<BakeryProduct> products = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(getProductsFile(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                BakeryProduct product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        products.sort(Comparator.comparingInt(BakeryProduct::getId));
        return products;
    }

    private List<BakeryOrder> loadOrders() throws IOException {
        Files.createDirectories(getDataDir());
        if (Files.notExists(getOrdersFile())) {
            seedOrders();
        }
        List<BakeryOrder> orders = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(getOrdersFile(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                BakeryOrder order = parseOrder(line);
                if (order != null) {
                    orders.add(order);
                }
            }
        }
        orders.sort(Comparator.comparingInt(BakeryOrder::getId));
        return orders;
    }

    private void writeProducts(List<BakeryProduct> products) throws IOException {
        Files.createDirectories(getDataDir());
        try (BufferedWriter writer = Files.newBufferedWriter(getProductsFile(), StandardCharsets.UTF_8)) {
            for (BakeryProduct product : products) {
                writer.write(serializeProduct(product));
                writer.newLine();
            }
        }
    }

    private void writeOrders(List<BakeryOrder> orders) throws IOException {
        Files.createDirectories(getDataDir());
        try (BufferedWriter writer = Files.newBufferedWriter(getOrdersFile(), StandardCharsets.UTF_8)) {
            for (BakeryOrder order : orders) {
                writer.write(serializeOrder(order));
                writer.newLine();
            }
        }
    }

    private int nextProductId(List<BakeryProduct> products) {
        return products.stream().map(BakeryProduct::getId).max(Integer::compareTo).orElse(0) + 1;
    }

    private int nextOrderId(List<BakeryOrder> orders) {
        return orders.stream().map(BakeryOrder::getId).max(Integer::compareTo).orElse(0) + 1;
    }

    private BakeryProduct parseProduct(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length < 8) {
            return null;
        }
        return new BakeryProduct(
                Integer.parseInt(parts[0]),
                unescape(parts[1]),
                unescape(parts[2]),
                Double.parseDouble(parts[3]),
                Integer.parseInt(parts[4]),
                unescape(parts[7]),
                unescape(parts[6]),
                Boolean.parseBoolean(parts[5])
        );
    }

    private BakeryOrder parseOrder(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = line.split("\\|", -1);
        if (parts.length < 9) {
            return null;
        }
        return new BakeryOrder(
                Integer.parseInt(parts[0]),
                unescape(parts[1]),
                unescape(parts[2]),
                Integer.parseInt(parts[3]),
                Double.parseDouble(parts[4]),
                unescape(parts[5]),
                unescape(parts[6]),
                unescape(parts[7]),
                unescape(parts[8])
        );
    }

    private String serializeProduct(BakeryProduct product) {
        return String.join("|",
                Integer.toString(product.getId()),
                escape(product.getName()),
                escape(product.getCategory()),
                Double.toString(product.getPrice()),
                Integer.toString(product.getStock()),
                Boolean.toString(product.isFeatured()),
                escape(product.getImagePath()),
                escape(product.getDescription()));
    }

    private String serializeOrder(BakeryOrder order) {
        return String.join("|",
                Integer.toString(order.getId()),
                escape(order.getCustomerName()),
                escape(order.getProductName()),
                Integer.toString(order.getQuantity()),
                Double.toString(order.getUnitPrice()),
                escape(order.getContact()),
                escape(order.getDeliveryDate()),
                escape(order.getStatus()),
                escape(order.getNote()));
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    private String unescape(String value) {
        return Objects.requireNonNullElse(value, "").trim();
    }
}

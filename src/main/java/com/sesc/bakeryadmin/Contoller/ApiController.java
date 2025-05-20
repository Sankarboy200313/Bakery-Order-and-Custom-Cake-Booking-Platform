package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.Order;
import com.sesc.bakeryadmin.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final String ORDERS_FILE = "src/main/resources/data/orders.txt";
    private static final String USERS_FILE = "src/main/resources/data/users.txt";
    private static final String COMMENTS_FILE = "src/main/resources/data/comments.txt";
    private static final String PRODUCT_FILE = "src/main/resources/data/product.txt";

    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        List<String> users = FileUtil.readUsers(USERS_FILE);
        List<Order> orders = FileUtil.readOrders(ORDERS_FILE);
        List<String> comments = FileUtil.readComments(COMMENTS_FILE);
        
        double totalIncome = orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
        
        Map<String, Object> overview = new HashMap<>();
        overview.put("loginUsers", users.size());
        overview.put("orders", orders.size());
        overview.put("comments", comments.size());
        overview.put("income", String.format("$%.2f", totalIncome));
        
        return overview;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return FileUtil.readOrders(ORDERS_FILE);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        List<String> users = FileUtil.readUsers(USERS_FILE);
        List<Order> orders = FileUtil.readOrders(ORDERS_FILE);
        List<String> comments = FileUtil.readComments(COMMENTS_FILE);
        
        double totalIncome = orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
        
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("loginUsers", users.size());
        dashboard.put("orders", orders.size());
        dashboard.put("comments", comments.size());
        dashboard.put("income", String.format("$%.2f", totalIncome));
        dashboard.put("recentOrders", orders);
        
        return dashboard;
    }
} 
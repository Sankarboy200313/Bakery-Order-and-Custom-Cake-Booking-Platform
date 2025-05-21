package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.Dashboard;
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
public class DashboardController {
/* 
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        List<Order> orders = FileUtil.readOrders("src/main/resources/data/orders.txt");
        List<String> users = FileUtil.readUsers("src/main/resources/data/users.txt");
        List<String> comments = FileUtil.readComments("src/main/resources/data/comments.txt");
        
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
    }*/

    @GetMapping("/stats")
    public Dashboard getDashboardStats() {
        // Simulate reading from files
        List<Order> orders = FileUtil.readOrders("src/main/resources/data/orders.txt");
        int userCount = FileUtil.readUsers("src/main/resources/data/users.txt").size();
        int commentCount = FileUtil.readComments("src/main/resources/data/comments.txt").size();
        double income = orders.stream().mapToDouble(Order::getTotal).sum();

        return new Dashboard(userCount, orders.size(), commentCount, income, orders);
    }
}
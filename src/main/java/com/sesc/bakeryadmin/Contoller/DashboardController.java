package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.Dashboard;
import com.sesc.bakeryadmin.Model.Order;
import com.sesc.bakeryadmin.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/")
    public String dashboard() {
        return "index";
    }
/*
    @GetMapping("/orders")
    public String ordersPage() {
        return "ordersandreport";
    }

    @GetMapping("/users")
    public String usersPage() {
        return "UserManagement";
    }

    @GetMapping("/store")
    public String storePage() {
        return "Storedetails";
    }*/
    @GetMapping("/stats")
    public Dashboard getDashboardStats() {
        // Simulate reading from files
        List<Order> orders = FileUtil.readOrders("orders.txt");
        int userCount = FileUtil.readUsers("users.txt").size();
        int commentCount = FileUtil.readComments("comments.txt").size();
        double income = orders.stream().mapToDouble(Order::getTotal).sum();

        return new Dashboard(userCount, orders.size(), commentCount, income, orders);
    }
}

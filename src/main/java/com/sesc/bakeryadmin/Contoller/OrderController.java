package com.sesc.bakeryadmin.Contoller;

import com.sesc.bakeryadmin.Model.Order;
import com.sesc.bakeryadmin.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order-management")
@CrossOrigin(origins = "*") // Allow requests from frontend
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/data")
    public List<Map<String, Object>> getOrderChartData() {
        List<Order> orders = orderService.getAllOrders();
        Map<String, Double> dateRevenue = new HashMap<>();
        
        // Group orders by date and sum revenue
        for (Order order : orders) {
            String date = order.getDate();
            double total = order.getTotal();
            
            dateRevenue.put(date, dateRevenue.getOrDefault(date, 0.0) + total);
        }
        
        // Convert to list for chart
        List<Map<String, Object>> chartData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : dateRevenue.entrySet()) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", entry.getKey());
            point.put("revenue", entry.getValue());
            chartData.add(point);
        }
        
        return chartData;
    }
}

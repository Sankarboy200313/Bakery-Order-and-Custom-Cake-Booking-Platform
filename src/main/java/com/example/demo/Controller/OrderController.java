package com.example.demo.Controller;

import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String index() {
        return "orderFirst";
    }

    @GetMapping("customOrder")
    public String customOrderPage() {
        return "customOrder";
    }

    @GetMapping("summary")
    public String showSummaryPage(
            @RequestParam String flavor,
            @RequestParam String shape,
            @RequestParam(required = false) List<String> toppings,
            @RequestParam String message,
            Model model
    ) {
        model.addAttribute("flavor", flavor);
        model.addAttribute("shape", shape);
        model.addAttribute("toppings", toppings);
        model.addAttribute("message", message);

        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Flavor: ").append(flavor).append(", ");
        orderDetails.append("Shape: ").append(shape);
        if (toppings != null && !toppings.isEmpty()) {
            orderDetails.append(", Toppings: ").append(String.join(", ", toppings));
        }
        orderDetails.append(", Message: ").append(message);

        model.addAttribute("orderDetails", orderDetails.toString());

        return "summary";
    }
}

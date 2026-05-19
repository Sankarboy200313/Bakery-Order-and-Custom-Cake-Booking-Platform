package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.BakeryOrder;
import com.sesc.onlinebakerystore.model.BakeryProduct;
import com.sesc.onlinebakerystore.service.BakeryStoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class OrderController {
    private final BakeryStoreService bakeryStoreService;

    public OrderController(BakeryStoreService bakeryStoreService) {
        this.bakeryStoreService = bakeryStoreService;
    }

    @GetMapping("/order")
    public String order(Model model) throws IOException {
        List<BakeryProduct> products = bakeryStoreService.getProducts();
        List<BakeryOrder> orders = bakeryStoreService.getOrders();
        model.addAttribute("products", products);
        model.addAttribute("recentOrders", orders.stream().sorted((a, b) -> Integer.compare(b.getId(), a.getId())).limit(5).toList());
        model.addAttribute("featuredCount", products.stream().filter(BakeryProduct::isFeatured).count());
        return "order";
    }

    @PostMapping("/order/place")
    public String placeOrder(
            @RequestParam int productId,
            @RequestParam int quantity,
            @RequestParam String deliveryDate,
            @RequestParam String contact,
            @RequestParam(required = false, defaultValue = "") String note,
            HttpSession session,
            Model model
    ) throws IOException {
        BakeryProduct product = bakeryStoreService.findProductById(productId);
        if (product == null) {
            model.addAttribute("error", "Selected product was not found.");
            return order(model);
        }
        String customer = (String) session.getAttribute("loggedInUser");
        BakeryOrder bakeryOrder = new BakeryOrder(
                0,
                customer != null ? customer : "Guest",
                product.getName(),
                Math.max(quantity, 1),
                product.getPrice(),
                contact,
                deliveryDate,
                "Processing",
                note
        );
        bakeryStoreService.saveOrder(bakeryOrder);
        return "redirect:/order?success=1";
    }
}

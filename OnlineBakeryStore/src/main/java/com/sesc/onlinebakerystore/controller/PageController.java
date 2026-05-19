package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.BakeryProduct;
import com.sesc.onlinebakerystore.service.BakeryStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class PageController {
    private final BakeryStoreService bakeryStoreService;

    public PageController(BakeryStoreService bakeryStoreService) {
        this.bakeryStoreService = bakeryStoreService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/service")
    public String service(Model model) throws IOException {
        List<BakeryProduct> featuredProducts = bakeryStoreService.getFeaturedProducts();
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("featuredCount", featuredProducts.size());
        model.addAttribute("serviceHighlights", List.of(
                "Custom cake design consultation",
                "Same-day pickup and delivery",
                "Corporate catering trays",
                "Seasonal gift box subscriptions"
        ));
        return "service";
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

    @GetMapping("/chart")
    public String chart() {
        return "redirect:/admin-dashboard#analytics";
    }

    @GetMapping("/contactUs")
    public String contactUs() {
        return "contactUs";
    }
}

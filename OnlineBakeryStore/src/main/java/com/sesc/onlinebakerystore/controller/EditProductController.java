package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.BakeryProduct;
import com.sesc.onlinebakerystore.service.BakeryStoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class EditProductController {
    private final BakeryStoreService bakeryStoreService;

    public EditProductController(BakeryStoreService bakeryStoreService) {
        this.bakeryStoreService = bakeryStoreService;
    }

    @GetMapping("/admin/product-form")
    public String productForm(@RequestParam(required = false, defaultValue = "0") int id, HttpSession session, Model model) throws IOException {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        BakeryProduct product = bakeryStoreService.findProductById(id);
        if (product == null) {
            product = new BakeryProduct();
        }
        model.addAttribute("product", product);
        return "edit-product";
    }
}

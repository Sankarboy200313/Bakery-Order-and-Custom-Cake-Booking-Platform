package com.sesc.onlinebakerystore.controller;

import com.sesc.onlinebakerystore.model.BakeryOrder;
import com.sesc.onlinebakerystore.model.BakeryProduct;
import com.sesc.onlinebakerystore.model.User;
import com.sesc.onlinebakerystore.service.BakeryStoreService;
import com.sesc.onlinebakerystore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BakeryStoreService bakeryStoreService;

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }
        try {
            User user = userService.findByUsername(username);
            if (user != null && user.isAdmin()) {
                List<User> users = userService.readUsers();
                List<BakeryProduct> products = bakeryStoreService.getProducts();
                List<BakeryOrder> orders = bakeryStoreService.getOrders();
                if (users == null) {
                    users = new ArrayList<>();
                }
                model.addAttribute("users", users);
                model.addAttribute("products", products);
                model.addAttribute("orders", orders);
                model.addAttribute("totalUsers", users.size());
                model.addAttribute("totalProducts", products.size());
                model.addAttribute("totalOrders", orders.size());
                model.addAttribute("openOrders", bakeryStoreService.countOpenOrders());
                model.addAttribute("totalRevenue", bakeryStoreService.totalRevenue());
                model.addAttribute("totalRevenueLabel", String.format("$%.2f", bakeryStoreService.totalRevenue()));
                model.addAttribute("maxStock", Math.max(products.stream().map(BakeryProduct::getStock).max(Comparator.naturalOrder()).orElse(1), 1));
                model.addAttribute("recentOrders", orders.stream()
                        .sorted((a, b) -> Integer.compare(b.getId(), a.getId()))
                        .limit(5)
                        .toList());
                if (users.isEmpty()) {
                    model.addAttribute("error", "No users found in the system.");
                }
                return "admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error retrieving dashboard data: " + e.getMessage());
            model.addAttribute("users", new ArrayList<>());
            model.addAttribute("products", new ArrayList<>());
            model.addAttribute("orders", new ArrayList<>());
            model.addAttribute("recentOrders", new ArrayList<>());
            model.addAttribute("totalUsers", 0);
            model.addAttribute("totalProducts", 0);
            model.addAttribute("totalOrders", 0);
            model.addAttribute("openOrders", 0);
            model.addAttribute("totalRevenueLabel", "$0.00");
            model.addAttribute("maxStock", 1);
            return "admin-dashboard";
        }
    }

    @GetMapping("/admin/edit-user")
    public String showEditUserForm(@RequestParam String username, HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                User userToEdit = userService.findByUsername(username);
                if (userToEdit != null) {
                    model.addAttribute("user", userToEdit);
                    return "edit-user";
                }
                model.addAttribute("error", "User not found");
                return "redirect:/admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error retrieving user: " + e.getMessage());
            return "redirect:/admin-dashboard";
        }
    }

    @PostMapping("/admin/update-user")
    public String updateUser(
            @RequestParam String oldUsername,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String fullname,
            @RequestParam String telephoneNo,
            @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin,
            HttpSession session,
            Model model
    ) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                User updatedUser = new User(username, email, password, fullname, telephoneNo, isAdmin);
                userService.updateUser(oldUsername, updatedUser);
                return "redirect:/admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error updating user: " + e.getMessage());
            return "edit-user";
        }
    }

    @GetMapping("/admin/edit-product")
    public String showEditProductForm(@RequestParam int id, HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                BakeryProduct product = bakeryStoreService.findProductById(id);
                if (product != null) {
                    model.addAttribute("product", product);
                    return "edit-product";
                }
                model.addAttribute("error", "Product not found");
                return "redirect:/admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error retrieving product: " + e.getMessage());
            return "redirect:/admin-dashboard";
        }
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(
            @RequestParam(defaultValue = "0") int id,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam int stock,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam(required = false, defaultValue = "") String imagePath,
            @RequestParam(value = "featured", defaultValue = "false") boolean featured,
            HttpSession session,
            Model model
    ) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                bakeryStoreService.saveProduct(new BakeryProduct(id, name, category, price, stock, description, imagePath, featured));
                return "redirect:/admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error saving product: " + e.getMessage());
            return "redirect:/admin-dashboard";
        }
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@RequestParam int id, HttpSession session) throws IOException {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        User admin = userService.findByUsername(loggedInUser);
        if (admin != null && admin.isAdmin()) {
            bakeryStoreService.deleteProduct(id);
            return "redirect:/admin-dashboard";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/order/update-status")
    public String updateOrderStatus(
            @RequestParam int id,
            @RequestParam String status,
            HttpSession session,
            Model model
    ) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            User admin = userService.findByUsername(loggedInUser);
            if (admin != null && admin.isAdmin()) {
                bakeryStoreService.updateOrderStatus(id, status);
                return "redirect:/admin-dashboard";
            }
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("error", "Error updating order: " + e.getMessage());
            return "redirect:/admin-dashboard";
        }
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrder(@RequestParam int id, HttpSession session) throws IOException {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        User admin = userService.findByUsername(loggedInUser);
        if (admin != null && admin.isAdmin()) {
            bakeryStoreService.deleteOrder(id);
            return "redirect:/admin-dashboard";
        }
        return "redirect:/login";
    }
}

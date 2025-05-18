package com.sesc.onlinebakerystore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sesc.onlinebakerystore.service.UserService;

// @Controller indicates this class handles HTTP requests for page navigation
@Controller
public class PageController {

    @Autowired
    private UserService userService;

    // Displays the dashboard page
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "dashboard"; // Returns dashboard.html
    }

    // Displays the services page
    @GetMapping("/service")
    public String service(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "service"; // Returns service.html
    }

    // Displays the about us page
    @GetMapping("/aboutUs")
    public String aboutUs(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "aboutUs"; // Returns aboutUs.html
    }

    // Displays the chart page
    @GetMapping("/chart")
    public String chart(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "chart"; // Returns chart.html
    }

    // Displays the order page
    @GetMapping("/order")
    public String order(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "order"; // Returns order.html
    }

    // Displays the contact us page
    @GetMapping("/contactUs")
    public String contactUs(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        model.addAttribute("isLoggedIn", isLoggedIn != null ? isLoggedIn : false);
        return "contactUs"; // Returns contactUs.html
    }
}
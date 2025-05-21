package com.sesc.bakeryadmin.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling page navigation
 * Returns view templates (HTML)
 */
@Controller
public class PageController {
    
    @GetMapping("/")
    public String dashboard() {
        return "index";
    }
    
    @GetMapping("/store")
    public String storePage() {
        return "Storedetails";
    }
    
    @GetMapping("/orders-page")
    public String ordersPage() {
        return "ordersandreport";
    }
    
    @GetMapping("/users")
    public String usersPage() {
        return "UserManagement";
    }
}

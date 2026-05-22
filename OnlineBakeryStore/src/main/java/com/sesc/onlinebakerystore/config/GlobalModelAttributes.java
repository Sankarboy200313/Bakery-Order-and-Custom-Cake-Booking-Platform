package com.sesc.onlinebakerystore.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("isLoggedIn")
    public boolean isLoggedIn(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("isLoggedIn"));
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("isAdmin"));
    }
}

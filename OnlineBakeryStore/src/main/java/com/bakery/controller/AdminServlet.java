package com.bakery.controller;

import com.bakery.model.Order;
import com.bakery.model.OrderQueue;
import com.bakery.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderQueue orderQueue = FileUtil.loadOrders();
        request.setAttribute("orders", orderQueue.getAllOrders());
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderId = request.getParameter("orderId");

        OrderQueue orderQueue = FileUtil.loadOrders();

        if ("update".equals(action)) {
            String newStatus = request.getParameter("status");
            orderQueue.updateOrderStatus(orderId, newStatus);
        } else if ("complete".equals(action)) {
            // Remove completed order
            Order order = findOrderById(orderQueue, orderId);
            if (order != null) {
                orderQueue.getAllOrders().remove(order);
            }
        }

        FileUtil.saveOrders(orderQueue);
        response.sendRedirect("admin");
    }

    private Order findOrderById(OrderQueue orderQueue, String orderId) {
        for (Order order : orderQueue.getAllOrders()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
}
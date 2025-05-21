package com.bakery.controller;

import com.bakery.model.Order;
import com.bakery.model.OrderQueue;
import com.bakery.util.FileUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OrderServlet", value = {"/order", "/track-order"})
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/track-order".equals(path)) {
            String orderId = request.getParameter("orderId");
            OrderQueue orderQueue = FileUtil.loadOrders();
            Order order = findOrderById(orderQueue, orderId);

            request.setAttribute("order", order);
            request.getRequestDispatcher("/trackOrder.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/orderForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = new Order();
        order.setCustomerName(request.getParameter("name"));
        order.setEmail(request.getParameter("email"));
        order.setPhone(request.getParameter("phone"));
        order.setProduct(request.getParameter("product"));
        order.setSpecialRequests(request.getParameter("specialRequests"));
        order.setDeliveryDate(request.getParameter("deliveryDate"));
        order.setDeliveryTime(request.getParameter("deliveryTime"));
        order.setAddress(request.getParameter("address"));

        OrderQueue orderQueue = FileUtil.loadOrders();
        orderQueue.addOrder(order);
        FileUtil.saveOrders(orderQueue);

        response.sendRedirect("track-order?orderId=" + order.getId());
    }

    private Order findOrderById(OrderQueue orderQueue, String orderId) {
        if (orderId == null) return null;

        for (Order order : orderQueue.getAllOrders()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
}
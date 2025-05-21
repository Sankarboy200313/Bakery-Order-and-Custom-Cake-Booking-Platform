package com.bakery.model;

import java.util.LinkedList;

public class OrderQueue {
    private LinkedList<Order> orders;

    public OrderQueue() {
        this.orders = new LinkedList<>();
    }

    public void addOrder(Order order) {
        orders.addLast(order);
    }

    public Order getNextOrder() {
        return orders.pollFirst();
    }

    public Order peekNextOrder() {
        return orders.peekFirst();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }



    public LinkedList<Order> getAllOrders() {
        return new LinkedList<>(orders);
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                order.setStatus(newStatus);
                break;
            }
        }
    }
}



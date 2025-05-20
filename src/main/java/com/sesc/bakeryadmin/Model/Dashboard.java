package com.sesc.bakeryadmin.Model;

import java.util.List;

public class Dashboard {
    private int userCount;
    private int orderCount;
    private int commentCount;
    private double income;
    private List<Order> recentOrders;

    public Dashboard(int userCount, int orderCount, int commentCount, double income, List<Order> recentOrders) {
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.commentCount = commentCount;
        this.income = income;
        this.recentOrders = recentOrders;
    }
    
    public int getUserCount() {
        return userCount;
    }
    
    public int getOrderCount() {
        return orderCount;
    }
    
    public int getCommentCount() {
        return commentCount;
    }
    
    public double getIncome() {
        return income;
    }
    
    public List<Order> getRecentOrders() {
        return recentOrders;
    }
}

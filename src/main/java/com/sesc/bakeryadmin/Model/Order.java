package com.sesc.bakeryadmin.Model;

public class Order {
    private Long orderId;
    private String customer;
    private String date;
    private Double total;
    private String status;

    // Default constructor (required for frameworks like Jackson)
    public Order() {
    }

    // Parameterized constructor
    public Order(Long orderId, String customer, String date, Double total, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

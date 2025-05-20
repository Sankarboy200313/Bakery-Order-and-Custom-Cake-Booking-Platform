package com.sesc.bakeryadmin.Model;

public class Order {
    private Long orderId;
    private String customer;
    private String date;
    private Double total;
    private String status;

    public Order(Long orderId, String customer, String date, Double total, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getDate() {
        return date;
    }

    public Double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}

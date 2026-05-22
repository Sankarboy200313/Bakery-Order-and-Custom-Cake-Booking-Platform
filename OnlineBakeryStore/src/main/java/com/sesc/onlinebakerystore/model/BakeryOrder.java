package com.sesc.onlinebakerystore.model;

public class BakeryOrder {
    private int id;
    private String customerName;
    private String productName;
    private int quantity;
    private double unitPrice;
    private String contact;
    private String deliveryDate;
    private String status;
    private String note;

    public BakeryOrder() {
    }

    public BakeryOrder(int id, String customerName, String productName, int quantity, double unitPrice, String contact, String deliveryDate, String status, String note) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.contact = contact;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    public String getTotalPriceLabel() {
        return String.format("$%.2f", getTotalPrice());
    }
}

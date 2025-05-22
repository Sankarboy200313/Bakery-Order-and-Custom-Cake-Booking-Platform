package com.example.demo.model;

public class Payment
{
    private String order_Details;
    private String title;
    private  String fname;
    private  String lname;
    private  String email;
    private  String phone;
    private  String address;
    private  String paymentMethod;

    public String getOrder_Details() {
        return order_Details;
    }

    public void setOrder_Details(String order_Details) {
        this.order_Details = order_Details;
    }

    public String getTitle() {
        return title;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    @Override
    public String toString() {
        return "Order{" +
                "Order Details='" + order_Details + '\'' +
                ", Title='" + title + '\'' +
                ", First Name='" + fname + '\'' +
                ", Last Name='" + lname + '\'' +
                ", Email='" + email + '\'' +
                ", Phone='" + phone + '\'' +
                ", Address='" + address + '\'' +
                ", Payment Method='" + paymentMethod + '\'' +
                '}';
    }
}

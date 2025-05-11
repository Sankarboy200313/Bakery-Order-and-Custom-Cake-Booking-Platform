package com.example.oopcrud.models;

import java.util.Date;

// Base class for common attributes
public abstract class BaseModel {
    private String id;
    private String status;
    private Date createdAt;

    // Constructor
    public BaseModel() {
        this.createdAt = new Date();
        this.status = "Pending";
    }

    // Getters and Setters for encapsulation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Abstract method that child classes must implement
    public abstract boolean isValid();
}
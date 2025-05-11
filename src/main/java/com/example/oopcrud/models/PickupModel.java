package com.example.oopcrud.models;

import java.util.Date;
public class PickupModel extends BaseModel {
    // Private attributes for encapsulation
    private String pickupPersonName;
    private String pickupLocation;
    private Date pickupDate;
    private String contactNumber;
    private String email;

    // Default constructor
    public PickupModel() {
        super();
    }

    // Constructor with parameters
    public PickupModel(String pickupPersonName, String pickupLocation,
                       Date pickupDate, String contactNumber, String email) {
        super(); // Call parent constructor
        this.pickupPersonName = pickupPersonName;
        this.pickupLocation = pickupLocation;
        this.pickupDate = pickupDate;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters and Setters for encapsulation
    public String getPickupPersonName() {
        return pickupPersonName;
    }

    public void setPickupPersonName(String pickupPersonName) {
        this.pickupPersonName = pickupPersonName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean isValid() {
        // Basic validation logic
        return pickupPersonName != null && !pickupPersonName.isEmpty() &&
                pickupLocation != null && !pickupLocation.isEmpty() &&
                pickupDate != null &&
                contactNumber != null && !contactNumber.isEmpty();
    }


    public boolean isScheduledForToday() {
        if (pickupDate == null) {
            return false;
        }

        Date today = new Date();
        // Compare just the date part, not time
        return pickupDate.getYear() == today.getYear() &&
                pickupDate.getMonth() == today.getMonth() &&
                pickupDate.getDate() == today.getDate();
    }
}
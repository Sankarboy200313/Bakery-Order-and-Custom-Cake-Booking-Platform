package com.example.oopcrud.controllers;

import com.example.oopcrud.models.PickupModel;
import com.example.oopcrud.services.PickupService;
import com.example.oopcrud.utils.PickupBubbleSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pickups")
public class PickupController {

    @Autowired
    private PickupService pickupService;

    @PostMapping
    public ResponseEntity<String> createPickup(@RequestBody PickupModel pickup) {
        boolean result = pickupService.createPickup(pickup);
        if (result) {
            return new ResponseEntity<>("Pickup created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create pickup", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PickupModel>> getAllPickups(
            @RequestParam(required = false, defaultValue = "asc") String sort) {
        List<PickupModel> pickups;

        if ("desc".equalsIgnoreCase(sort)) {
            pickups = pickupService.getAllPickupsDescending();
        } else {
            pickups = pickupService.getAllPickups(); // Default ascending
        }

        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    @GetMapping("/raw")
    public ResponseEntity<List<PickupModel>> getAllPickupsRaw() {
        List<PickupModel> pickups = pickupService.getAllPickupsRaw();
        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    @GetMapping("/{personName}")
    public ResponseEntity<PickupModel> getPickupByPersonName(@PathVariable String personName) {
        PickupModel pickup = pickupService.getPickupByPersonName(personName);
        if (pickup != null) {
            return new ResponseEntity<>(pickup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{personName}")
    public ResponseEntity<String> updatePickup(
            @PathVariable String personName,
            @RequestBody PickupModel updatedPickup) {
        boolean result = pickupService.updatePickup(personName, updatedPickup);
        if (result) {
            return new ResponseEntity<>("Pickup updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update pickup", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{personName}")
    public ResponseEntity<String> deletePickup(@PathVariable String personName) {
        boolean result = pickupService.deletePickup(personName);
        if (result) {
            return new ResponseEntity<>("Pickup deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete pickup", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PickupModel>> getPickupsByStatus(
            @PathVariable String status,
            @RequestParam(required = false, defaultValue = "asc") String sort) {

        List<PickupModel> pickups = pickupService.getPickupsByStatus(status);

        // Apply sorting if needed
        if ("desc".equalsIgnoreCase(sort)) {
            pickups = PickupBubbleSort.sortByDateDescending(pickups);
        } else {
            pickups = PickupBubbleSort.sortByDateAscending(pickups);
        }

        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    @GetMapping("/dateRange")
    public ResponseEntity<List<PickupModel>> getPickupsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false, defaultValue = "asc") String sort) {

        List<PickupModel> pickups = pickupService.getPickupsByDateRange(startDate, endDate);

        // Apply sorting if needed
        if ("desc".equalsIgnoreCase(sort)) {
            pickups = PickupBubbleSort.sortByDateDescending(pickups);
        } else {
            pickups = PickupBubbleSort.sortByDateAscending(pickups);
        }

        return new ResponseEntity<>(pickups, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PickupModel> getPickupById(@PathVariable String id) {
        PickupModel pickup = pickupService.getPickupById(id);
        if (pickup != null) {
            return new ResponseEntity<>(pickup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deletePickupById(@PathVariable String id) {
        boolean result = pickupService.deletePickupById(id);
        if (result) {
            return new ResponseEntity<>("Pickup deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete pickup", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> updatePickupById(@PathVariable String id, @RequestBody PickupModel updatedPickup) {
        boolean result = pickupService.updatePickupById(id, updatedPickup);
        if (result) {
            return new ResponseEntity<>("Pickup updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update pickup", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/")
    public String showPickupListPage() {
        return "pickup-details";  // returns pickup-details.jsp
    }


}
package com.example.oopcrud.services;

import com.example.oopcrud.models.PickupModel;
import com.example.oopcrud.utils.PickupBubbleSort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class PickupService {
    private static final String FILE_PATH = "pickup_data.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean createPickup(PickupModel pickup) {
        try {
            if (!pickup.isValid()) {
                return false;
            }

            String id = UUID.randomUUID().toString();
            pickup.setId(id);
            List<PickupModel> pickups = getAllPickups();
            pickups.add(pickup);

            return saveAllPickups(pickups);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public PickupModel getPickupByPersonName(String personName) {
        try {
            List<PickupModel> pickups = getAllPickups();
            return pickups.stream()
                    .filter(p -> p.getPickupPersonName().equals(personName))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PickupModel> getAllPickups() {
        try {
            List<PickupModel> pickups = new ArrayList<>();
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                file.createNewFile();
                return pickups;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                PickupModel currentPickup = null;
                Map<String, String> pickupData = new HashMap<>();

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        if (!pickupData.isEmpty()) {
                            currentPickup = createPickupFromMap(pickupData);
                            pickups.add(currentPickup);
                            pickupData.clear();
                        }
                        continue;
                    }

                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        pickupData.put(parts[0].trim(), parts[1].trim());
                    }
                }

                if (!pickupData.isEmpty()) {
                    currentPickup = createPickupFromMap(pickupData);
                    pickups.add(currentPickup);
                }
            }

            return PickupBubbleSort.sortByDateAscending(pickups);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PickupModel> getAllPickupsDescending() {
        try {
            List<PickupModel> pickups = getAllPickups();
            return PickupBubbleSort.sortByDateDescending(pickups);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PickupModel> getAllPickupsRaw() {
        try {
            List<PickupModel> pickups = new ArrayList<>();
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                file.createNewFile();
                return pickups;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                PickupModel currentPickup = null;
                Map<String, String> pickupData = new HashMap<>();

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        if (!pickupData.isEmpty()) {
                            currentPickup = createPickupFromMap(pickupData);
                            pickups.add(currentPickup);
                            pickupData.clear();
                        }
                        continue;
                    }

                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        pickupData.put(parts[0].trim(), parts[1].trim());
                    }
                }

                if (!pickupData.isEmpty()) {
                    currentPickup = createPickupFromMap(pickupData);
                    pickups.add(currentPickup);
                }
            }

            return pickups;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean updatePickup(String personName, PickupModel updatedPickup) {
        try {
            if (!updatedPickup.isValid()) {
                return false;
            }

            List<PickupModel> pickups = getAllPickupsRaw();
            boolean found = false;

            for (int i = 0; i < pickups.size(); i++) {
                if (pickups.get(i).getPickupPersonName().equals(personName)) {
                    pickups.set(i, updatedPickup);
                    found = true;
                    break;
                }
            }

            if (found) {
                return saveAllPickups(pickups);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePickup(String personName) {
        try {
            List<PickupModel> pickups = getAllPickupsRaw();
            int initialSize = pickups.size();

            pickups = pickups.stream()
                    .filter(p -> !p.getPickupPersonName().equals(personName))
                    .collect(Collectors.toList());

            if (pickups.size() < initialSize) {
                return saveAllPickups(pickups);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveAllPickups(List<PickupModel> pickups) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PickupModel pickup : pickups) {
                writer.write("id: " + (pickup.getId() != null ? pickup.getId() : ""));
                writer.newLine();
                writer.write("personName: " + pickup.getPickupPersonName());
                writer.newLine();
                writer.write("location: " + pickup.getPickupLocation());
                writer.newLine();
                writer.write("date: " + DATE_FORMAT.format(pickup.getPickupDate()));
                writer.newLine();
                writer.write("status: " + pickup.getStatus());
                writer.newLine();
                writer.write("contactNumber: " + pickup.getContactNumber());
                writer.newLine();
                writer.write("email: " + (pickup.getEmail() != null ? pickup.getEmail() : ""));
                writer.newLine();
                writer.write("createdAt: " + DATE_FORMAT.format(pickup.getCreatedAt()));
                writer.newLine();
                writer.newLine();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PickupModel createPickupFromMap(Map<String, String> data) {
        PickupModel pickup = new PickupModel();
        try {
            if (data.containsKey("id")) {
                pickup.setId(data.get("id"));
            }

            if (data.containsKey("personName")) {
                pickup.setPickupPersonName(data.get("personName"));
            }

            if (data.containsKey("location")) {
                pickup.setPickupLocation(data.get("location"));
            }

            if (data.containsKey("date")) {
                pickup.setPickupDate(DATE_FORMAT.parse(data.get("date")));
            }

            if (data.containsKey("status")) {
                pickup.setStatus(data.get("status"));
            }

            if (data.containsKey("contactNumber")) {
                pickup.setContactNumber(data.get("contactNumber"));
            }

            if (data.containsKey("email")) {
                pickup.setEmail(data.get("email"));
            }

            if (data.containsKey("createdAt")) {
                pickup.setCreatedAt(DATE_FORMAT.parse(data.get("createdAt")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pickup;
    }

    public List<PickupModel> getPickupsByStatus(String status) {
        try {
            List<PickupModel> pickups = getAllPickups();
            return pickups.stream()
                    .filter(p -> p.getStatus().equals(status))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PickupModel> getPickupsByDateRange(Date startDate, Date endDate) {
        try {
            List<PickupModel> pickups = getAllPickups();
            return pickups.stream()
                    .filter(p -> {
                        Date pickupDate = p.getPickupDate();
                        return pickupDate != null &&
                                !pickupDate.before(startDate) &&
                                !pickupDate.after(endDate);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public PickupModel getPickupById(String id) {
        try {
            List<PickupModel> pickups = getAllPickups();
            return pickups.stream()
                    .filter(p -> p.getId() != null && p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletePickupById(String id) {
        try {
            List<PickupModel> pickups = getAllPickupsRaw();
            int initialSize = pickups.size();

            pickups = pickups.stream()
                    .filter(p -> p.getId() == null || !p.getId().equals(id))
                    .collect(Collectors.toList());

            if (pickups.size() < initialSize) {
                return saveAllPickups(pickups);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePickupById(String id, PickupModel updatedPickup) {
        try {
            if (!updatedPickup.isValid() || id == null || id.isEmpty()) {
                return false;
            }

            List<PickupModel> pickups = getAllPickupsRaw();
            boolean found = false;

            for (int i = 0; i < pickups.size(); i++) {
                PickupModel existingPickup = pickups.get(i);
                if (id.equals(existingPickup.getId())) {
                    updatedPickup.setId(id); // Maintain ID
                    pickups.set(i, updatedPickup);
                    found = true;
                    break;
                }
            }

            if (found) {
                return saveAllPickups(pickups);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

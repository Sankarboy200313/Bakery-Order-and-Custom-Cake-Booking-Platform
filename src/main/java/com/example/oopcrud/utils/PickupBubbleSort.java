package com.example.oopcrud.utils;

import com.example.oopcrud.models.PickupModel;
import java.util.List;

public class PickupBubbleSort {

    // Sort pickups in ascending order by pickup date
    public static List<PickupModel> sortByDateAscending(List<PickupModel> pickups) {
        int n = pickups.size();

        // Clone the list to avoid modifying the original
        List<PickupModel> sortedList = List.copyOf(pickups);

        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // If current date is after next date, swap them
                if (pickups.get(j).getPickupDate() != null &&
                        pickups.get(j + 1).getPickupDate() != null &&
                        pickups.get(j).getPickupDate().after(pickups.get(j + 1).getPickupDate())) {

                    // Swap elements
                    PickupModel temp = pickups.get(j);
                    pickups.set(j, pickups.get(j + 1));
                    pickups.set(j + 1, temp);
                }
            }
        }

        return pickups;
    }

    // Sort pickups in descending order by pickup date
    public static List<PickupModel> sortByDateDescending(List<PickupModel> pickups) {
        int n = pickups.size();

        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // If current date is before next date, swap them (for descending order)
                if (pickups.get(j).getPickupDate() != null &&
                        pickups.get(j + 1).getPickupDate() != null &&
                        pickups.get(j).getPickupDate().before(pickups.get(j + 1).getPickupDate())) {

                    // Swap elements
                    PickupModel temp = pickups.get(j);
                    pickups.set(j, pickups.get(j + 1));
                    pickups.set(j + 1, temp);
                }
            }
        }

        return pickups;
    }

    // Handle null dates (pushing them to the end)
    public static List<PickupModel> sortByDateWithNullHandling(List<PickupModel> pickups) {
        int n = pickups.size();

        // Bubble sort algorithm with null handling
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Handle cases where dates might be null
                if (shouldSwap(pickups.get(j), pickups.get(j + 1))) {
                    // Swap elements
                    PickupModel temp = pickups.get(j);
                    pickups.set(j, pickups.get(j + 1));
                    pickups.set(j + 1, temp);
                }
            }
        }

        return pickups;
    }

    // Helper method to determine if elements should be swapped
    private static boolean shouldSwap(PickupModel pickup1, PickupModel pickup2) {
        // Case 1: pickup1 has null date, pickup2 has date - swap to push null to end
        if (pickup1.getPickupDate() == null && pickup2.getPickupDate() != null) {
            return true;
        }

        // Case 2: both have dates - compare normally
        if (pickup1.getPickupDate() != null && pickup2.getPickupDate() != null) {
            return pickup1.getPickupDate().after(pickup2.getPickupDate());
        }

        // Case 3: pickup1 has date, pickup2 has null - keep as is
        // Case 4: both are null - keep as is
        return false;
    }
}
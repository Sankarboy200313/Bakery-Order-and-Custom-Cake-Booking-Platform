package com.example.demo.Controller;

import com.example.demo.model.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class paymentAndBillingController {

    private Payment lastOrder; // Temporarily holds the last order for editing

    @GetMapping("/")
    public String index() {
        return "customOrder";
    }

    @GetMapping("/summary")
    public String showSummaryPage(
            @RequestParam String flavor,
            @RequestParam String shape,
            @RequestParam(required = false) List<String> toppings,
            @RequestParam String message,
            Model model
    ) {
        model.addAttribute("flavor", flavor);
        model.addAttribute("shape", shape);
        model.addAttribute("toppings", toppings);
        model.addAttribute("message", message);

        // Build order details string to pass to payment page
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Flavor: ").append(flavor).append(", ");
        orderDetails.append("Shape: ").append(shape);
        if (toppings != null && !toppings.isEmpty()) {
            orderDetails.append(", Toppings: ").append(String.join(", ", toppings));
        }
        orderDetails.append(", Message: ").append(message);

        model.addAttribute("orderDetails", orderDetails.toString());

        return "summary";
    }

    @GetMapping("/payment")
    public String showPaymentForm(
            @RequestParam(required = false) String order_Details,
            @RequestParam(required = false) String fname,
            @RequestParam(required = false) String lname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            Model model) {

        Payment payment = new Payment();
        payment.setOrder_Details(order_Details);
        payment.setFname(fname);
        payment.setLname(lname);
        payment.setEmail(email);
        payment.setPhone(phone);
        payment.setAddress(address);

        model.addAttribute("payment", payment);
        return "payment";
    }

    @PostMapping("/order")
    public String userRegistration(@ModelAttribute Payment user, RedirectAttributes redirectAttributes) {
        saveOrderToFile(user);
        lastOrder = user;
        redirectAttributes.addFlashAttribute("order", user);
        return "redirect:/order-details";
    }

    @GetMapping("/order-details")
    public String showOrderDetailsPage(Model model) {
        if (!model.containsAttribute("order") && lastOrder != null) {
            model.addAttribute("order", lastOrder);
        }
        return "order-details";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Payment updatedOrder, RedirectAttributes redirectAttributes) {
        updateLastOrderInFile(updatedOrder);
        lastOrder = updatedOrder;
        redirectAttributes.addFlashAttribute("order", updatedOrder);
        return "redirect:/order-details";
    }

    @PostMapping("/delete")
    public String deleteOrder(RedirectAttributes redirectAttributes) {
        String filePath = "src/main/resources/Storage/payments.txt";

        try {
            File file = new File(filePath);
            if (file.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                String[] orders = content.split("(?=----- New Order)");

                if (orders.length > 1) {
                    StringBuilder newContent = new StringBuilder();
                    for (int i = 0; i < orders.length - 1; i++) {
                        newContent.append(orders[i].trim()).append("\n\n");
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write(newContent.toString().trim());
                    }
                    System.out.println("Last order deleted successfully.");
                } else {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write("");
                    }
                    System.out.println("All orders deleted.");
                }

                lastOrder = null;
            } else {
                System.out.println("Order file does not exist.");
            }
        } catch (IOException e) {
            System.err.println("Error deleting order from file.");
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message", "Your order has been cancelled.");
        return "redirect:/cancel-success";
    }

    @GetMapping("/cancel-success")
    public String cancelSuccess() {
        return "cancel-success";
    }

    private void saveOrderToFile(Payment order) {
        String directoryPath = "src/main/resources/Storage";
        String filePath = directoryPath + "/payments.txt";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("----- New Order (" + timestamp + ") -----\n");
            writer.write(order.toString() + "\n");
            writer.write("----- End Order -----\n\n");
            System.out.println("Order saved to: " + new File(filePath).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing order to file.");
            e.printStackTrace();
        }
    }

    private void updateLastOrderInFile(Payment updatedOrder) {
        String filePath = "src/main/resources/Storage/payments.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Order file does not exist.");
                return;
            }

            List<String> lines = java.nio.file.Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();

            int startIdx = -1;
            int endIdx = -1;

            for (int i = lines.size() - 1; i >= 0; i--) {
                if (lines.get(i).contains("----- End Order -----")) {
                    endIdx = i;
                } else if (lines.get(i).startsWith("----- New Order")) {
                    startIdx = i;
                    break;
                }
            }

            if (startIdx != -1 && endIdx != -1 && endIdx > startIdx) {
                updatedLines.addAll(lines.subList(0, startIdx));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = LocalDateTime.now().format(formatter);
                updatedLines.add("----- New Order (" + timestamp + ") -----");
                updatedLines.add(updatedOrder.toString());
                updatedLines.add("----- End Order -----");
                updatedLines.add("");

                java.nio.file.Files.write(file.toPath(), updatedLines);
                System.out.println("Last order updated successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error updating order in file.");
            e.printStackTrace();
        }
    }
}

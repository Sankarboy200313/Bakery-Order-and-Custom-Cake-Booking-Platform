package com.example.demo.Controller;

import com.example.demo.modle.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/order")
    public String userRegistration(@ModelAttribute Order user, RedirectAttributes redirectAttributes) {
        saveOrderToFile(user);
        redirectAttributes.addFlashAttribute("order", user); // Send order details via flash attributes
        return "redirect:/order-details"; // Redirect to avoid resubmission
    }

    @GetMapping("/order-details")
    public String showOrderDetailsPage() {
        return "order-details"; // This view will use the flash attribute "order"
    }

    private void saveOrderToFile(Order order) {
        String directoryPath = "data";
        String filePath = directoryPath + "/orders.txt";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("----- New Order (" + timestamp + ") -----\n");
            writer.write(order.toString() + "\n");
            writer.write("----- End Order -----\n");
            writer.write("\n");
            System.out.println("Order saved to: " + new File(filePath).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing order to file.");
            e.printStackTrace();
        }
    }

    @PostMapping("/delete")
    public String deleteOrder(RedirectAttributes redirectAttributes) {
        String filePath = "data/orders.txt";
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
            } else {
                System.out.println("Order file does not exist.");
            }
        } catch (IOException e) {
            System.err.println("Error deleting order from file.");
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message", "Your order has been cancelled.");
        return "redirect:/cancel-success"; // Redirect to prevent repeated deletion
    }

    @GetMapping("/cancel-success")
    public String cancelSuccess() {
        return "cancel-success";
    }
}

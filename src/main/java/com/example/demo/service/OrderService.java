package com.example.demo.service;

import com.example.demo.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private Payment lastOrder;

    public Payment getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(Payment order) {
        this.lastOrder = order;
    }
}

package com.tjapp.examples.demo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentStore {
    private Map<String, Payment> payments = new HashMap<>();

    public void insert(Payment payment) {
        this.payments.put(payment.getId(), payment);
    }

    public Payment getPayment(String id) {
        return this.payments.get(id);
    }

    public void delete(String id) {
        this.payments.remove(id);
    }
}

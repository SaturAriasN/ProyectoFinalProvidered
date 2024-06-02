package com.example.proyectofinal.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    @Expose
    private ArrayList<Order> orders;

    public Cart() {
        orders = new ArrayList<>();
    }

    public Cart(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Map<String, ArrayList<Order>> getOrdersProvider() {
        Map<String, ArrayList<Order>> map = new HashMap<>();

        for (Order order : this.orders) {
            if (map.containsKey(order.getProduct().getProvider().getName())) {
                map.get(order.getProduct().getProvider().getName()).add(order);
            } else {
                map.put(order.getProduct().getProvider().getName(), new ArrayList<>());
            }
        }
        return map;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "orders=" + orders +
                '}';
    }
}

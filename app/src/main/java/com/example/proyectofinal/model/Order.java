package com.example.proyectofinal.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    @Expose
    private Product product;
    @Expose
    private int stock;

    public Order(Product product, int stock) {
        this.product = product;
        this.stock = stock;
    }

    public Order() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product +
                ", stock=" + stock +
                '}';
    }
}

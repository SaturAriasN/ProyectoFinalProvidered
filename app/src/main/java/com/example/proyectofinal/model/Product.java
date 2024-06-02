package com.example.proyectofinal.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Product implements Serializable {

    @Expose
    private String name;
    @Expose
    private double price;
    private Provider provider;
    @Expose
    private String category;
    @Expose
    private String desc;
    @Expose
    private int amount;
    @Expose
    private String url;

    public Product(String name, double price, Provider provider, String url) {
        this.name = name;
        this.price = price;
        this.provider = provider;
        this.url = url;
    }

    public Product(String name, double price, Provider provider, String category, String desc, int amount, String url) {
        this.name = name;
        this.price = price;
        this.provider = provider;
        this.category = category;
        this.desc = desc;
        this.amount = amount;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", provider='" + provider + '\'' +
                ", category='" + category + '\'' +
                ", desc='" + desc + '\'' +
                ", amount=" + amount +
                ", url='" + url + '\'' +
                '}';
    }
}

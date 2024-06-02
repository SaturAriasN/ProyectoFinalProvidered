package com.example.proyectofinal.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Provider implements Serializable {
    @Expose
    private String name;
    @Expose
    private String type;
    @Expose
    private String desc;
    @Expose
    private String urlImage;

    private ArrayList<Product> products = new ArrayList<>();

    public Provider() {
    }

    public Provider(String name) {
        this.name = name;
    }

    public Provider(String name, String type, String desc, String urlImage, ArrayList<Product> products) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.urlImage = urlImage;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(name, provider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

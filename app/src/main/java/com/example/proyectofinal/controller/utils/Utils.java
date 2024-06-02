package com.example.proyectofinal.controller.utils;

import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import com.example.proyectofinal.R;
import com.example.proyectofinal.model.Order;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Utils {

    public static NavController navController;
    public static NavOptions navOptions = new NavOptions.Builder()
            .setPopUpTo(R.id.nav_category, false) // Elimina todos los fragmentos hasta el menú principal
            .build();

    public static double calcularSuma(ArrayList<Order> list) {
        double sum = 0;
        for (Order order : list) {
            sum += order.getStock() * order.getProduct().getPrice();
        }
        return sum;
    }


    public static ArrayList<String> listNames(String jsonString) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.getString(i));
            }
            return arrayList;
        } catch (JSONException e) {
        }
        return null;
    }

}

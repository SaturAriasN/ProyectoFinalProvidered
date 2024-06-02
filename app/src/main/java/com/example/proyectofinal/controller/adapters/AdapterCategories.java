package com.example.proyectofinal.controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.R;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Product;

import java.util.ArrayList;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {

    private ArrayList<Category> categories;

    private AdapterProducts adapterProducts;

    private Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton imageProduct;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageProduct = view.findViewById(R.id.categoryButton);
        }


    }

    public AdapterCategories(ArrayList<Category> categories, AdapterProducts adapterProducts) {
        this.categories = categories;
        this.adapterProducts = adapterProducts;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_category, viewGroup, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Category p = categories.get(position);

        Glide.with(context)
                .load(p.getUrl())
                .into(viewHolder.imageProduct);
        viewHolder.imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterProducts.setProducts(p.getProducts());
                adapterProducts.setCategory(p);
                adapterProducts.notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}

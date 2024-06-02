package com.example.proyectofinal.controller.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {


    private ArrayList<Product> products;
    private Category category;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageProduct;
        public final TextView nameProduct;
        public final TextView priceProduct;
        public final TextView providerProduct;

        public final ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);

            imageProduct = view.findViewById(R.id.modelImage);
            nameProduct = view.findViewById(R.id.modelNombre);
            priceProduct = view.findViewById(R.id.modelPrecio);
            providerProduct = view.findViewById(R.id.modelProvider);
            constraintLayout = view.findViewById(R.id.constraintproduct);
        }


    }

    public AdapterProducts(Category category) {
        this.products = category.getProducts();
        this.category = category;

    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_product, viewGroup, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Product p = products.get(position);

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("product", p);
                bundle.putSerializable("category", category);


                Utils.navController.navigate(R.id.nav_info_product, bundle);
            }
        });

        viewHolder.nameProduct.setText(p.getName());
        viewHolder.priceProduct.setText("" + p.getPrice());
        viewHolder.providerProduct.setText(p.getProvider().getName());
        Glide.with(context)
                .load(p.getUrl())
                .into(viewHolder.imageProduct);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}

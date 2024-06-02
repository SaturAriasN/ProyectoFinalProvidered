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
import com.example.proyectofinal.model.Provider;

import java.util.ArrayList;

public class AdapterInfoProducts extends RecyclerView.Adapter<AdapterInfoProducts.ViewHolder> {

    private ArrayList<Product> products;
    private Category category;
    private Context context;

    private Provider provider;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageProduct;
        public final TextView nameProduct;

        private ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);

            imageProduct = view.findViewById(R.id.modelInfoImage);
            nameProduct = view.findViewById(R.id.modelInfoNombre);
            constraintLayout = view.findViewById(R.id.constraintinfoproduct);
        }


    }

    public AdapterInfoProducts(Category category) {
        this.products = category.getProducts();
        this.category = category;
    }

    public AdapterInfoProducts(Provider provider) {
        this.provider = provider;
        this.products = provider.getProducts();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_info_product, viewGroup, false);
        this.context = view.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Product p = products.get(position);

        viewHolder.nameProduct.setText(p.getName());
        Glide.with(context)
                .load(p.getUrl())
                .into(viewHolder.imageProduct);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", p);
                bundle.putSerializable("category", category);
                bundle.putSerializable("provider", provider);
                Utils.navController.navigate(R.id.nav_info_product, bundle, Utils.navOptions);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}

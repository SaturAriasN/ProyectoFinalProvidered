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
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;

import java.util.ArrayList;

public class AdapterProviders extends RecyclerView.Adapter<AdapterProviders.ViewHolder> {

    private ArrayList<Provider> providers;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout constraintLayout;
        public final ImageView imageProvider;
        public final TextView nameProvider;
        public final TextView typeProvider;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            constraintLayout = view.findViewById(R.id.constraintProvider);
            imageProvider = view.findViewById(R.id.modelImageProvider);
            nameProvider = view.findViewById(R.id.modelNameProvider);
            typeProvider = view.findViewById(R.id.modelTypeProvider);
        }


    }

    public AdapterProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_provider, viewGroup, false);

        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final Provider p = providers.get(position);

        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("provider", p);


                Utils.navController.navigate(R.id.nav_info_provider, bundle);
            }
        });

        viewHolder.nameProvider.setText(p.getName());
        viewHolder.typeProvider.setText(p.getType());
        Glide.with(context)
                .load(p.getUrlImage())
                .into(viewHolder.imageProvider);

    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

}

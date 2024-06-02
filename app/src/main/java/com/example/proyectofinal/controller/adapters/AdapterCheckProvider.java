package com.example.proyectofinal.controller.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.R;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.model.Cart;
import com.example.proyectofinal.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class AdapterCheckProvider extends RecyclerView.Adapter<AdapterCheckProvider.ViewHolder> {

    private ArrayList<String> providers;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameProvider;
        public final ImageView buttonOk;
        public final ImageView buttonKo;


        public ViewHolder(View view) {
            super(view);

            nameProvider = view.findViewById(R.id.ProviderTickName);
            buttonOk = view.findViewById(R.id.ProviderTickButton);
            buttonKo = view.findViewById(R.id.buttonKo);
        }


    }

    public AdapterCheckProvider(ArrayList<String> providers) {
        this.providers = providers;
    }

    public void removePosition(int pos) {
        providers.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_provider_tick, viewGroup, false);
        this.context = view.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        String p = providers.get(position);

        viewHolder.nameProvider.setText(p);

        AdapterCheckProvider adapterCheckProvider = this;
        int pos = position;
        viewHolder.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BBDD.reportProvider(adapterCheckProvider, pos, context, "https://providered.es/actualizarUser.php", p, "0");
                BBDD.reportProviderComplete(context, "https://providered.es/actualizarRoleUser.php", p);

            }
        });

        viewHolder.buttonKo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BBDD.reportProvider(adapterCheckProvider, pos, context, "https://providered.es/actualizarUser.php", p, "0");

            }
        });


    }
    @Override
    public int getItemCount() {
        return providers.size();
    }

}

package com.example.proyectofinal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.controller.adapters.AdapterInfoProducts;
import com.example.proyectofinal.controller.adapters.AdapterProviders;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.databinding.InfoProviderBinding;
import com.example.proyectofinal.model.Provider;

public class InfoProvidersFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView nameProvider;
    private TextView typeProvider;
    private TextView descProvider;
    private ImageView imageProvider;


    private InfoProviderBinding binding;
    private View root;

    private Provider provider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = InfoProviderBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        provider = (Provider) getArguments().getSerializable("provider");

        init();


        return root;
    }


    private void init() {
        nameProvider = binding.nameProviderInfo;
        nameProvider.setText(provider.getName());
        typeProvider = binding.typeProviderInfo;
        typeProvider.setText(provider.getType());
        descProvider = binding.descProvider;
        descProvider.setText(provider.getDesc());
        imageProvider = binding.imageProviderInfo;
        Glide.with(this)
                .load(provider.getUrlImage())
                .into(imageProvider);

        recyclerView = binding.recyclerViewProductsProvider;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterInfoProducts adapterProducts = new AdapterInfoProducts(provider);
        recyclerView.setAdapter(adapterProducts);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.proyectofinal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.controller.adapters.AdapterProducts;
import com.example.proyectofinal.controller.adapters.AdapterProviders;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.databinding.FragmentProvidersBinding;

public class ProvidersFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentProvidersBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProvidersBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        init();
        return root;
    }


    private void init() {

        recyclerView = binding.recylcerViewProviders;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        AdapterProviders adapterProducts = new AdapterProviders(BBDD.providers);
        recyclerView.setAdapter(adapterProducts);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
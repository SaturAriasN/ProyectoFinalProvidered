package com.example.proyectofinal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.controller.adapters.AdapterCategories;
import com.example.proyectofinal.controller.adapters.AdapterProducts;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.databinding.FragmentCategoryBinding;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;


    private RecyclerView recyclerView;

    private RecyclerView recyclerViewCategories;

    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        init();


        return root;
    }

    private void init() {


        recyclerView = binding.recyclerProducts;


        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        AdapterProducts adapterProducts = new AdapterProducts(new Category());
        if (BBDD.categories.size() > 0) {
            adapterProducts.setCategory(BBDD.categories.get(1));
        }


        recyclerView.setAdapter(adapterProducts);

        recyclerViewCategories = binding.recyclerViewCategories;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewCategories.setLayoutManager(linearLayoutManager);

        AdapterCategories adapterCat = new AdapterCategories(BBDD.categories, adapterProducts);
        recyclerViewCategories.setAdapter(adapterCat);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.proyectofinal.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.R;
import com.example.proyectofinal.controller.adapters.AdapterCart;
import com.example.proyectofinal.controller.adapters.AdapterInfoProducts;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.databinding.FragmentCartBinding;
import com.example.proyectofinal.databinding.InfoProductBinding;
import com.example.proyectofinal.model.Cart;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Order;
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;
import com.google.gson.Gson;

import org.json.JSONException;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView priceCart;

    private FragmentCartBinding binding;
    private Button buttonCart;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        init();


        return root;
    }


    private void init() {


        priceCart = binding.TotalPriceCart;
        buttonCart = binding.BuyButtonCart;


        recyclerView = binding.RecyclerViewCart;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterCart adapterProducts;

        SharedPreferences sharedPref = getActivity().getSharedPreferences("BBDD", Context.MODE_PRIVATE);

        String myCart = sharedPref.getString("CART", "");

        Gson gson = new Gson();
        Cart cart = new Cart();
        if (!myCart.equals("")) {
            cart = gson.fromJson(myCart, Cart.class);
        }
        Cart finalCart = cart;
        adapterProducts = new AdapterCart(cart, priceCart);

        Cart finalCart1 = cart;
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    BBDD.sendMail(getContext(), "https://providered.es/mail.php", finalCart, sharedPref.getString("EMAIL", ""));
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("CART");
                    editor.apply();
                    priceCart.setText("0.0€");
                    recyclerView.setAdapter(new AdapterCart(new Cart(), priceCart));

                } catch (JSONException e) {
                    System.out.println();
                }
            }
        });

        priceCart.setText((Utils.calcularSuma(cart.getOrders()) + "€"));
        recyclerView.setAdapter(adapterProducts);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
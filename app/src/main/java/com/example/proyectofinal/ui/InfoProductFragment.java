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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.R;
import com.example.proyectofinal.controller.adapters.AdapterInfoProducts;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.databinding.InfoProductBinding;
import com.example.proyectofinal.model.Cart;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Order;
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

public class InfoProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView nameProduct;
    private TextView categoryProduct;
    private TextView descProduct;
    private ImageView imageProduct;
    private Button stockButton;
    private EditText stockAmount;

    private TextView providerProduct;


    private InfoProductBinding binding;
    private View root;

    private Product product;
    private Category category;
    private Provider provider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = InfoProductBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        product = (Product) getArguments().getSerializable("product");
        category = (Category) getArguments().getSerializable("category");
        provider = (Provider) getArguments().getSerializable("provider");


        init();


        return root;
    }


    private void init() {
        nameProduct = binding.nameProductInfo;
        nameProduct.setText(product.getName());
        categoryProduct = binding.typeProductInfo;
        categoryProduct.setText(product.getCategory());
        descProduct = binding.descProduct;
        descProduct.setText(product.getDesc());
        imageProduct = binding.imageProductInfo;
        Glide.with(this)
                .load(product.getUrl())
                .into(imageProduct);

        stockButton = binding.stockProductButton;
        providerProduct = binding.textViewProviderInfoProduct;
        providerProduct.setText(product.getProvider().getName());
        providerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("provider", product.getProvider());


                Utils.navController.navigate(R.id.nav_info_provider, bundle);
            }
        });

        stockButton = binding.stockProductButton;
        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("BBDD", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();


                String myCart = sharedPref.getString("CART", "");

                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                Cart cart = new Cart();
                if (!myCart.equals("")) {
                    cart = gson.fromJson(myCart, Cart.class);
                }
                Order order = new Order();
                order.setStock(Integer.parseInt(stockAmount.getText().toString()));
                order.setProduct(product);
                cart.addOrder(order);
                editor.putString("CART", gson.toJson(cart));
                editor.apply();
                Toast.makeText(getContext(), "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        stockAmount = binding.amountProductStock;
        stockAmount.setText("1");


        recyclerView = binding.recyclerViewProductsProducts;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterInfoProducts adapterProducts;

        if (category != null) {
            adapterProducts = new AdapterInfoProducts(category);
        } else {
            adapterProducts = new AdapterInfoProducts(provider);
        }
        recyclerView.setAdapter(adapterProducts);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
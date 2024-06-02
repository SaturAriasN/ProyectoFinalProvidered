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
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.model.Cart;
import com.example.proyectofinal.model.Order;
import com.example.proyectofinal.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    private ArrayList<Order> products;
    private TextView priceCart;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageProduct;
        public final TextView nameProduct;
        public final TextView priceProduct;
        public final TextView amountProduct;
        public final ImageView button;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageProduct = view.findViewById(R.id.CartProductImage);
            nameProduct = view.findViewById(R.id.CartProductName);
            priceProduct = view.findViewById(R.id.CartProductPrice);
            amountProduct = view.findViewById(R.id.CartProductAmount);
            button = view.findViewById(R.id.CartDeleteButton);
        }


    }

    public AdapterCart(Cart cart, TextView priceCart) {
        this.products = cart.getOrders();
        this.priceCart = priceCart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_cart, viewGroup, false);
        this.context = view.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Order p = products.get(position);

        viewHolder.nameProduct.setText(p.getProduct().getName());
        Glide.with(context)
                .load(p.getProduct().getUrl())
                .into(viewHolder.imageProduct);
        viewHolder.priceProduct.setText("" + p.getProduct().getPrice());
        viewHolder.amountProduct.setText("" + p.getStock());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).equals(p)) {
                        products.remove(i);

                        SharedPreferences sharedPref = context.getSharedPreferences("BBDD", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();


                        String myCart = sharedPref.getString("CART", "");

                        Gson gson = new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create();
                        Cart cart = new Cart();
                        if (!myCart.equals("")) {
                            cart = gson.fromJson(myCart, Cart.class);
                        }

                        cart.setOrders(products);
                        editor.putString("CART", gson.toJson(cart));
                        editor.apply();

                        priceCart.setText((Utils.calcularSuma(cart.getOrders()) + "€"));

                        notifyItemRemoved(i);
                        return;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}

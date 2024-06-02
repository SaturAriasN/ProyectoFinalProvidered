package com.example.proyectofinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.controller.adapters.AdapterCheckProvider;
import com.example.proyectofinal.controller.bbdd.BBDD;
import com.example.proyectofinal.controller.utils.Utils;
import com.example.proyectofinal.model.Category;
import com.example.proyectofinal.model.Product;
import com.example.proyectofinal.model.Provider;
import com.example.proyectofinal.ui.LoadingDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);

        onClicksButtonBar();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View header = navigationView.getHeaderView(0);

        SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);

        String u = sharedPreferences.getString("USER", "null");
        String e = sharedPreferences.getString("EMAIL", "null");


        TextView name = header.findViewById(R.id.nameDrawner);
        name.setText(u);
        TextView email = header.findViewById(R.id.emailDrawner);
        email.setText(e);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_providers, R.id.nav_settings, R.id.nav_report, R.id.nav_category)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Utils.navController = navController;
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    private void onClicksButtonBar() {
        Activity activity = this;

        SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);
        //administrator
        //shop_manager

        String role = sharedPreferences.getString("ROLE", "null");


        if (role.equals("administrator")) {
            binding.appBarMain.fab.setVisibility(View.VISIBLE);
            binding.appBarMain.fabprovider.setVisibility(View.VISIBLE);
            binding.appBarMain.fabcat.setVisibility(View.VISIBLE);
            binding.appBarMain.fabproduct.setVisibility(View.VISIBLE);
        }

        if (role.equals("shop_manager")) {
            binding.appBarMain.fab.setVisibility(View.VISIBLE);
            binding.appBarMain.fabproduct.setVisibility(View.VISIBLE);
        }
        binding.appBarMain.fabcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateCategory(activity);
            }
        });
        binding.appBarMain.fabproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateProduct(activity);
            }
        });
        binding.appBarMain.fabprovider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListProviders(activity);
            }
        });
    }

    private void showCreateProduct(Activity activity) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater.
        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_create_product, null));


        Dialog dialog = builder.create();


        dialog.show();


        EditText editName = dialog.findViewById(R.id.ProductNameAdd);
        EditText editPrice = dialog.findViewById(R.id.ProductPriceAdd);
        EditText editDesc = dialog.findViewById(R.id.ProductDescAdd);
        EditText editStock = dialog.findViewById(R.id.ProductStockAdd);


        Spinner spinnerProvider = dialog.findViewById(R.id.ProductProviderAdd);
        Spinner spinnerCategoryr = dialog.findViewById(R.id.ProductCategoryAdd);
        SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("ROLE", "null");
        String user = sharedPreferences.getString("USER", "null");

        SpinnerAdapter adapterProvider = new ArrayAdapter<Provider>(this, android.R.layout.simple_spinner_item, BBDD.providers);

        if (role.equals("shop_manager")) {
            ArrayList<Provider> ne = new ArrayList<>(BBDD.providers);
            ne.removeIf(provider -> !provider.getName().contains(user));
            adapterProvider = new ArrayAdapter<Provider>(this, android.R.layout.simple_spinner_item, ne);

        }
        spinnerProvider.setAdapter(adapterProvider);

        SpinnerAdapter adapterCategory = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, BBDD.categories);
        spinnerCategoryr.setAdapter(adapterCategory);

        Button button = dialog.findViewById(R.id.ProductButtonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category a = (Category) spinnerCategoryr.getSelectedItem();

                Product product = new Product();
                product.setName(editName.getText().toString());
                product.setPrice(Double.valueOf(editPrice.getText().toString()));
                product.setDesc(editDesc.getText().toString());
                product.setProvider((Provider) spinnerProvider.getSelectedItem());
                product.setCategory(((Category) spinnerCategoryr.getSelectedItem()).getName());

                int id = ((Category) spinnerCategoryr.getSelectedItem()).getId();
                String provi = ((Provider) spinnerProvider.getSelectedItem()).getName();
                product.setUrl("https://providered.es/wp-content/uploads/2024/05/PCProvider.png");
                BBDD.createProduct(activity, "https://providered.es/wp-json/wc/v3/products", product, id, provi);
                dialog.dismiss();
            }
        });


    }

    private void showCreateCategory(Activity activity) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_create_category, null));

        Dialog dialog = builder.create();


        dialog.show();

        EditText editName = dialog.findViewById(R.id.CategoryNameAdd);

        Button button = dialog.findViewById(R.id.CategoryButtonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Category category = new Category();
                category.setName(editName.getText().toString());
                category.setUrl("https://providered.es/wp-content/uploads/2024/05/PCProvider.png");
                BBDD.createCategory(activity, "https://providered.es/wp-json/wc/v3/products/categories", category);
                dialog.dismiss();
            }
        });


    }


    private void showListProviders(Activity activity) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_provider, null));

        Dialog dialog = builder.create();

        dialog.show();

        RecyclerView list = dialog.findViewById(R.id.listProvider);
        list.setLayoutManager(new LinearLayoutManager(this));
        BBDD.listProviders(this, "https://providered.es/listarUsers.php", list);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {

            Utils.navController.navigate(R.id.nav_cart);
        } else if (id == R.id.action_settings) {
            SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            BBDD.categories.clear();
            BBDD.providers.clear();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
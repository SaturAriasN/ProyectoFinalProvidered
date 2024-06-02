package com.example.proyectofinal;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofinal.controller.bbdd.BBDD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);


        BBDD.getProducts(this, "https://providered.es/wp-json/wc/v3/products");

    }

    public void login(View vista) {
        SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);
        String users = sharedPreferences.getString("USER", user.getText().toString());
        String passw = sharedPreferences.getString("PASS", pass.getText().toString());

        BBDD.getLogin(this, "https://providered.es/wp-includes/login.php?user_login=" + users + "&user_pass=" + passw, this);


    }

    public void ok(String role, String mail) {

        SharedPreferences sharedPreferences = getSharedPreferences("BBDD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", user.getText().toString());
        editor.putString("PASS", pass.getText().toString());
        if(role.split("\"").length>1){
            role = role.split("\"")[1];
        }
        editor.putString("ROLE", role);
        editor.putString("EMAIL", mail);


        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    public void registrar(View vista) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
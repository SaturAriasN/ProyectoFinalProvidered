package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofinal.controller.bbdd.BBDD;

public class RegisterActivity extends AppCompatActivity {


    private EditText user;
    private EditText pass;
    private EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        init();


    }

    private void init() {
        user = findViewById(R.id.regName);
        pass = findViewById(R.id.regPassword);
        mail = findViewById(R.id.regMail);
    }

    public void registrar(View vista) {

        BBDD.registerUser(this, "https://providered.es/crearUser.php", user.getText().toString(), mail.getText().toString(),  pass.getText().toString());

    }
}
package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button registerButton, loginButton, recipesListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        registerButton = findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton = findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        recipesListButton = findViewById(R.id.recipesListBtn);
        recipesListButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RecipeListActivity.class);
            startActivity(intent);
        });
    }
}
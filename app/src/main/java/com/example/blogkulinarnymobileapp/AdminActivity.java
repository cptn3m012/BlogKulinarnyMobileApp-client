package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.TileAdapter;
import com.example.blogkulinarnymobileapp.TileData;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView adminHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminHeaderTextView = findViewById(R.id.adminHeaderTextView);
        adminHeaderTextView.setTextColor(getResources().getColor(android.R.color.white)); // Aktualizacja koloru tekstu

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<TileData> data = new ArrayList<>();
        data.add(new TileData("Przepisy", "zarządzaj wszystkimi przepisami"));
        data.add(new TileData("Użytkownicy", "zarządzaj wszystkimi użytkownikami"));

        TileAdapter tileAdapter = new TileAdapter(data);
        recyclerView.setAdapter(tileAdapter);
        tileAdapter.setOnItemClickListener(new TileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Tworzenie i inicjalizacja intentu dla nowej aktywności
                //Intent intent = new Intent(AdminActivity.this, RecipeDetails.class);
                // Przekazanie danych przepisu do nowej aktywności
                //intent.putExtra("recipe", recipe);
                // Uruchomienie nowej aktywności
                //startActivity(intent);
                Toast.makeText(AdminActivity.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(tileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView userHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userHeaderTextView = findViewById(R.id.userHeaderTextView);
        userHeaderTextView.setTextColor(getResources().getColor(android.R.color.white)); // Aktualizacja koloru tekstu

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
                Toast.makeText(UserActivity.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(tileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.Admin.ManageUsersActivity;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.Adapters.TileAdapter;
import com.example.blogkulinarnymobileapp.Adapters.TileData;

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
        data.add(new TileData("Profil", "Edytuj swój profil"));
        data.add(new TileData("Przepisy", "zobacz swoje przepisy"));

        TileAdapter tileAdapter = new TileAdapter(data);
        recyclerView.setAdapter(tileAdapter);
        tileAdapter.setOnItemClickListener(new TileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent editProfile = new Intent(UserActivity.this, EditUserActivity.class);
                        startActivity(editProfile);
                        break;
                    case 1:
                        Intent uzytkownicyIntent = new Intent(UserActivity.this, ManageUsersActivity.class);
                        startActivity(uzytkownicyIntent);
                        break;
                }
            }
        });

        recyclerView.setAdapter(tileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Admin.AdminActivity;
import com.example.blogkulinarnymobileapp.Admin.ManageUsersActivity;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.Adapters.TileAdapter;
import com.example.blogkulinarnymobileapp.Adapters.TileData;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import java.util.ArrayList;
import java.util.List;

import Home.HomeActivity;

public class UserActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private RecyclerView recyclerView;

    private TextView userHeaderTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userHeaderTextView = findViewById(R.id.userHeaderTextView);
        userHeaderTextView.setTextColor(getResources().getColor(android.R.color.white)); // Aktualizacja koloru tekstu

        recyclerView = findViewById(R.id.recyclerView);

        logoutButton = findViewById(R.id.logoutButton);

        sessionManagement = SessionManagement.getInstance(UserActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

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
                        Intent uzytkownicyIntent = new Intent(UserActivity.this, ShowUserRecipes.class);
                        startActivity(uzytkownicyIntent);
                        break;
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserActivity.this, "Zostałeś wylogowany pomyślnie!", Toast.LENGTH_SHORT).show();
                sessionManagement.removeSession();
                Intent homeIntent = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        recyclerView.setAdapter(tileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
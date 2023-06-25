package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.Adapters.TileAdapter;
import com.example.blogkulinarnymobileapp.Adapters.TileData;
import com.example.blogkulinarnymobileapp.RecipeDetails;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import java.util.ArrayList;
import java.util.List;

import Home.HomeActivity;

public class AdminActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private RecyclerView AdminRecyclerView;
    private RecyclerView UserRecyclerView;
    private TextView adminHeaderTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminHeaderTextView = findViewById(R.id.adminHeaderTextView);
        adminHeaderTextView.setTextColor(getResources().getColor(android.R.color.white)); // Aktualizacja koloru tekstu

        AdminRecyclerView = findViewById(R.id.AdminRecyclerView);
        UserRecyclerView = findViewById(R.id.UserRecyclerView);

        logoutButton = findViewById(R.id.logoutButton);

        sessionManagement = SessionManagement.getInstance(AdminActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        //admin
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        AdminRecyclerView.setLayoutManager(layoutManager);

        List<TileData> data = new ArrayList<>();
        data.add(new TileData("Przepisy", "zarządzaj wszystkimi przepisami"));
        data.add(new TileData("Użytkownicy", "zarządzaj wszystkimi użytkownikami"));
        data.add(new TileData("Kategorie", "zarządzaj wszystkimi kategoriami"));

        TileAdapter tileAdapter = new TileAdapter(data);
        AdminRecyclerView.setAdapter(tileAdapter);

        //uzytkownik
        List<TileData> dataUser = new ArrayList<>();
        dataUser.add(new TileData("Profil", "Edytuj swój profil"));
        dataUser.add(new TileData("Przepisy", "zobacz swoje przepisy"));

        TileAdapter UserAdapter = new TileAdapter(dataUser);
        UserRecyclerView.setAdapter(UserAdapter);
        tileAdapter.setOnItemClickListener(new TileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent przepisyIntent = new Intent(AdminActivity.this, ManageRecipesActivity.class);
                        startActivity(przepisyIntent);
                        break;
                    case 1:
                        Intent uzytkownicyIntent = new Intent(AdminActivity.this, ManageUsersActivity.class);
                        startActivity(uzytkownicyIntent);
                        break;
                    case 2:
                        Intent kategorieIntent = new Intent(AdminActivity.this, CategoriesActivity.class);
                        startActivity(kategorieIntent);
                        break;
                }
            }
        });

        UserAdapter.setOnItemClickListener(new TileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                switch (position) {
                    case 0:
                        //Intent przepisyIntent = new Intent(AdminActivity.this, ManageRecipesActivity.class);
                        //startActivity(przepisyIntent);
                        break;
                    case 1:
                        //Intent uzytkownicyIntent = new Intent(AdminActivity.this, ManageUsersActivity.class);
                        //startActivity(uzytkownicyIntent);
                        break;
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Zostałeś wylogowany pomyślnie!", Toast.LENGTH_SHORT).show();
                sessionManagement.removeSession();
                Intent homeIntent = new Intent(AdminActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        AdminRecyclerView.setAdapter(tileAdapter);
        AdminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserRecyclerView.setAdapter(UserAdapter);
        UserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

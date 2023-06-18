package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.Adapters.TileAdapter;
import com.example.blogkulinarnymobileapp.Adapters.TileData;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView AdminRecyclerView;
    private RecyclerView UserRecyclerView;
    private TextView adminHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminHeaderTextView = findViewById(R.id.adminHeaderTextView);
        adminHeaderTextView.setTextColor(getResources().getColor(android.R.color.white)); // Aktualizacja koloru tekstu

        AdminRecyclerView = findViewById(R.id.AdminRecyclerView);
        UserRecyclerView = findViewById(R.id.UserRecyclerView);

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

        AdminRecyclerView.setAdapter(tileAdapter);
        AdminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserRecyclerView.setAdapter(UserAdapter);
        UserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

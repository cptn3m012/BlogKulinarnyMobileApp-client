package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.blogkulinarnymobileapp.Models.Ranks;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.User;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_list);

            recyclerView = findViewById(R.id.recyclerView);

            // Utwórz przykładową listę przepisów
            List<Recipe> recipeList = new ArrayList<>();

            // Sprawdź, czy lista przepisów jest pusta
            if (recipeList.isEmpty()) {
                // Dodaj domyślny przepis
                Recipe defaultRecipe = new Recipe(
                        1,
                        true,
                        "Domyślny przepis",
                        "https://example.com/image.jpg",
                        "Opis domyślnego przepisu",
                        1,
                        30,
                        4,
                        1,
                        new User(1,"Autor domyślnego przepisu","1234","a", true, Ranks.USER," "),
                        Collections.emptyList()
                );
                recipeList.add(defaultRecipe);
            }

            // Utwórz i ustaw adapter
            adapter = new RecipeAdapter(recipeList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}

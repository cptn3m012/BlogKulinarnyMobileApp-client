package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetails extends AppCompatActivity {

    private LinearLayout stepsLayout;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        stepsLayout = findViewById(R.id.stepsLayout);

        // Odczytanie przekazanych danych przepisu z obiektu Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            Recipe recipe = intent.getParcelableExtra("recipe");

            // Wyświetlenie informacji o przepisie w odpowiednich widokach
            TextView titleTextView = findViewById(R.id.titleTextView);
            titleTextView.setText(recipe.getTitle());

            // Wykorzystaj bibliotekę Picasso lub inny mechanizm do ładowania obrazków
            ImageView imageView = findViewById(R.id.imageView);
            Picasso.get().load(recipe.getImageURL()).into(imageView);

            TextView difficultyLabel = findViewById(R.id.difficultyLabel);
            if (recipe.getDifficulty() == 1){
                difficultyLabel.setText("Łatwy");
                difficultyLabel.setTextColor(Color.rgb(0, 153, 0));
            } else if (recipe.getDifficulty() == 2) {
                difficultyLabel.setText("Średni");
                difficultyLabel.setTextColor(Color.rgb(255, 255, 0));
            } else if (recipe.getDifficulty() == 3) {
                difficultyLabel.setText("Trudny");
                difficultyLabel.setTextColor(Color.rgb(255, 102, 0));
            } else {
                difficultyLabel.setText("Bardzo trudny");
                difficultyLabel.setTextColor(Color.rgb(255, 0, 0));
            }

            TextView portionsTextView = findViewById(R.id.portionsTextView);
            portionsTextView.setText(String.valueOf(recipe.getPortions()));

            TextView avgTimeTextView = findViewById(R.id.avgTimeTextView);
            avgTimeTextView.setText(String.valueOf(recipe.getAvgTime()) + "min");

            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(recipe.getDescription());

            // Retrieve the steps list from the recipe
            List<RecipeElements> stepsList = recipe.getStepsList();

            // Set up the RecyclerView for steps
            RecyclerView stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
            StepAdapter stepsAdapter = new StepAdapter(stepsList);
            stepsRecyclerView.setAdapter(stepsAdapter);
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.Adapters.StarAdapter;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity {
    Spinner rateStar;

    private LinearLayout stepsLayout;
    private TextView titleTextView;
    private ImageView imageView;
    private TextView difficultyLabel;
    private TextView portionsTextView;
    private TextView avgTimeTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private RecyclerView stepsRecyclerView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        stepsLayout = findViewById(R.id.stepsLayout);
        titleTextView = findViewById(R.id.titleTextView);
        imageView = findViewById(R.id.imageView);
        difficultyLabel = findViewById(R.id.difficultyLabel);
        portionsTextView = findViewById(R.id.portionsTextView);
        avgTimeTextView = findViewById(R.id.avgTimeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        rateStar = findViewById(R.id.rateStarSpinner);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            Recipe recipe = intent.getParcelableExtra("recipe");
            displayRecipeDetails(recipe);
            setupStepsRecyclerView(recipe.getStepsList());
        }

        String[] array2 = {"0", "1", "2", "3","4","5"};

        StarAdapter adapterStars = new StarAdapter(this, 1, array2);
        rateStar.setAdapter(adapterStars);
    }

    private void displayRecipeDetails(Recipe recipe) {
        titleTextView.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImageURL()).into(imageView);

        int difficulty = recipe.getDifficulty();
        if (difficulty == 1) {
            setDifficultyLabel("Łatwy", Color.rgb(0, 153, 0));
        } else if (difficulty == 2) {
            setDifficultyLabel("Średni", Color.rgb(255, 255, 0));
        } else if (difficulty == 3) {
            setDifficultyLabel("Trudny", Color.rgb(255, 102, 0));
        } else {
            setDifficultyLabel("Bardzo trudny", Color.rgb(255, 0, 0));
        }

        portionsTextView.setText(String.valueOf(recipe.getPortions()));
        avgTimeTextView.setText(recipe.getAvgTime() + "min");
        descriptionTextView.setText(recipe.getDescription());
    }

    private void setDifficultyLabel(String text, int color) {
        difficultyLabel.setText(text);
        difficultyLabel.setTextColor(color);
    }

    private void setupStepsRecyclerView(List<RecipeElements> stepsList) {
        List<RecipeElements> stepListNew = new ArrayList<>();
        for (int i = 1; i < stepsList.size(); i++) {
            stepListNew.add(stepsList.get(i));
        }

        StepAdapter stepsAdapter = new StepAdapter(stepListNew);
        ingredientsTextView.setText(stepsList.get(0).description);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
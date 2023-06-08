package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeDetails extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Odczytanie przekazanych danych przepisu z obiektu Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            Recipe recipe = intent.getParcelableExtra("recipe");

            // Wyświetlenie informacji o przepisie w odpowiednich widokach
            TextView titleTextView = findViewById(R.id.titleTextView);
            titleTextView.setText(recipe.getTitle());

            //ImageView imageView = findViewById(R.id.imageView);
            // Wykorzystaj bibliotekę Picasso lub inny mechanizm do ładowania obrazków
            //Picasso.get().load(recipe.getImageURL()).into(imageView);

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

           /* // Wyświetlanie kroków przepisu
            LinearLayout stepsLayout = findViewById(R.id.stepsLayout);
            for (String step : recipe.getSteps()) {
                TextView stepTextView = new TextView(this);
                stepTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                stepTextView.setText(step);
                stepsLayout.addView(stepTextView);
            }*/
        }
    }
}
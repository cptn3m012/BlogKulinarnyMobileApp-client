package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.blogkulinarnymobileapp.Models.Ranks;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.Models.RecipesCategory;
import com.example.blogkulinarnymobileapp.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = findViewById(R.id.recyclerView);

        // Utwórz przykładową listę przepisów
        List<Recipe> recipeList = new ArrayList<>();

        LoadRecipeTask loadRecipeTask = new LoadRecipeTask();
        loadRecipeTask.execute(recipeList);

        // Utwórz i ustaw adapter
        adapter = new RecipeAdapter(recipeList, RecipeListActivity.this);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                // Tworzenie i inicjalizacja intentu dla nowej aktywności
                Intent intent = new Intent(RecipeListActivity.this, RecipeDetails.class);
                // Przekazanie danych przepisu do nowej aktywności
                intent.putExtra("recipe", recipe);
                // Uruchomienie nowej aktywności
                startActivity(intent);
            }

            @Override
            public void onLockButtonClick(Recipe recipe) {

            }

            @Override
            public void onCommentButtonClick(Recipe recipe) {

            }

            @Override
            public void onDeleteButtonClick(Recipe recipe) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class LoadRecipeTask extends AsyncTask<List<Recipe>, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(List<Recipe>... params) {
            List<Recipe> recipeList = params[0];
            String url = "http://10.0.2.2:5000/loadRecipes";

            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parsowanie odpowiedzi JSON i tworzenie obiektów Recipe
                    JSONObject responseJson = new JSONObject(response.toString());
                    JSONArray recipeArray = responseJson.getJSONArray("recipes");
                    for (int i = 0; i < recipeArray.length(); i++) {
                        JSONObject recipeJson = recipeArray.getJSONObject(i);
                        Recipe recipe = new Recipe();
                        recipe.setId(recipeJson.getInt("id"));
                        recipe.setAccepted(recipeJson.getBoolean("isAccepted"));
                        recipe.setTitle(recipeJson.getString("title"));
                        recipe.setImageURL(recipeJson.getString("imageURL"));
                        recipe.setDescription(recipeJson.getString("description"));
                        recipe.setDifficulty(recipeJson.getInt("difficulty"));
                        recipe.setAvgTime(recipeJson.getInt("avgTime"));
                        recipe.setPortions(recipeJson.getInt("portions"));
                        recipe.setUserId(recipeJson.getInt("userId"));

                        // Parsowanie kroków (stepList)
                        JSONArray stepsArray = recipeJson.getJSONArray("steps");
                        List<RecipeElements> stepList = new ArrayList<>();
                        for (int j = 0; j < stepsArray.length(); j++) {
                            JSONObject stepJson = stepsArray.getJSONObject(j);
                            RecipeElements step = new RecipeElements();
                            step.setImageURL(stepJson.getString("imageURL"));
                            step.setDescription(stepJson.getString("description"));
                            step.setNoOfList(stepJson.getInt("noOfList"));
                            stepList.add(step);
                        }
                        recipe.setStepsList(stepList);
                        recipeList.add(recipe);
                    }
                }
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }
            return recipeList;

        }
        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            super.onPostExecute(recipeList);
            adapter.notifyDataSetChanged();
        }
    }

}
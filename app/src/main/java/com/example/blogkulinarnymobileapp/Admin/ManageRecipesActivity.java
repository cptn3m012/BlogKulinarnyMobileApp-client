package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Models.Category;
import com.example.blogkulinarnymobileapp.Models.Comment;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.RecipeAdapter;
import com.example.blogkulinarnymobileapp.RecipeDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = findViewById(R.id.recyclerView);

        List<Recipe> recipeList = new ArrayList<>();

        ManageRecipesActivity.LoadRecipeTask loadRecipeTask = new ManageRecipesActivity.LoadRecipeTask();
        loadRecipeTask.execute(recipeList);


        adapter = new RecipeAdapter(recipeList, ManageRecipesActivity.this);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {

                Intent intent = new Intent(ManageRecipesActivity.this, RecipeDetails.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }

            @Override
            public void onLockButtonClick(Recipe recipe) {
                UpdateRecipeStatusTask urst = new UpdateRecipeStatusTask();
                urst.execute(recipe);
                recreate();

            }

            @Override
            public void onCommentButtonClick(Recipe recipe) {
                Toast.makeText(ManageRecipesActivity.this, "comm", Toast.LENGTH_SHORT).show();
                recreate();
            }

            @Override
            public void onDeleteButtonClick(Recipe recipe) {
                Toast.makeText(ManageRecipesActivity.this, "del", Toast.LENGTH_SHORT).show();
                recreate();
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

                        // Parsowanie komentarzy
                        JSONArray commentsArray = recipeJson.getJSONArray("comments");
                        List<Comment> commentList = new ArrayList<>();
                        for (int k = 0; k < commentsArray.length(); k++) {
                            JSONObject commentJson = commentsArray.getJSONObject(k);
                            Comment comment = new Comment();
                            comment.setText(commentJson.getString("text"));
                            comment.setRate(commentJson.getInt("rate"));
                            commentList.add(comment);
                        }
                        recipe.setCommentsList(commentList);

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

    private class UpdateRecipeStatusTask extends AsyncTask<Recipe, Void, Void> {

        @Override
        protected Void doInBackground(Recipe... recipes) {
            Recipe recipe = recipes[0];
            String url = "http://10.0.2.2:5000/updateRecipeState";

            try {

                // Tworzenie obiektu JSON z informacją o przepisie i jego zmienionym stanie
                JSONObject recipeJson = new JSONObject();
                recipeJson.put("id", recipe.getId());
                recipeJson.put("isAccepted", !recipe.isAccepted());

                // Wysyłanie żądania HTTP do serwera
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(recipeJson.toString());
                writer.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("Update", "User state updated successfully");
                }

                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Models.Comments;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.RecipeAdapter;
import com.example.blogkulinarnymobileapp.RecipeDetails;
import com.example.blogkulinarnymobileapp.RecipeListActivity;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

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
import java.util.List;

import Home.LoginActivity;

public class ShowUserRecipes extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Recipe> recipeList;
    private RecipeAdapter adapter;
    private SearchView searchView;

    private int rank, id;

    private Context context;

    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        sessionManagement = SessionManagement.getInstance(ShowUserRecipes.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        // Utwórz przykładową listę przepisów
        recipeList = new ArrayList<>();

        ShowUserRecipes.LoadRecipeTask loadRecipeTask = new ShowUserRecipes.LoadRecipeTask();
        loadRecipeTask.execute(recipeList, id);

        // Utwórz i ustaw adapter
        adapter = new RecipeAdapter(recipeList, ShowUserRecipes.this, getApplicationContext());
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                // Tworzenie i inicjalizacja intentu dla nowej aktywności
                Intent intent = new Intent(ShowUserRecipes.this, RecipeDetails.class);
                // Przekazanie danych przepisu do nowej aktywności
                intent.putExtra("recipe", recipe);
                // Uruchomienie nowej aktywności
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void filterList(String text) {
        List<Recipe> filteredList = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            if (recipe.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "Nie znaleziono danych", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private class LoadRecipeTask extends AsyncTask<Object, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Object... params) {
            List<Recipe> recipeList = (List<Recipe>) params[0];
            int userId = Integer.parseInt(params[1].toString());
            String url = "http://10.0.2.2:5000/loadRecipesForLoggedUser";

            String jsonInputString = "{\"user_id\": \"" + userId + "\"}";


            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonInputString.getBytes());
                outputStream.flush();

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
                        recipe.setId(recipeJson.getInt("recipeIdentifier"));
                        recipe.setAccepted(recipeJson.getBoolean("isAccepted"));
                        recipe.setTitle(recipeJson.getString("title"));
                        recipe.setImageURL(recipeJson.getString("imageURL"));
                        recipe.setDescription(recipeJson.getString("description"));
                        recipe.setDifficulty(recipeJson.getInt("difficulty"));
                        recipe.setAvgTime(recipeJson.getInt("avgTime"));
                        recipe.setPortions(recipeJson.getInt("portions"));
                        recipe.setUserId(recipeJson.getInt("userId"));
                        recipe.setAuthor(recipeJson.getString("u.login"));

                        JSONArray categoryArray = recipeJson.getJSONArray("categories");
                        List<String> categoryList = new ArrayList<>();
                        for (int k = 0; k < categoryArray.length(); k++){
                            categoryList.add(categoryArray.getString(k));
                        }

                        // Parsowanie komentarzy
                        JSONArray commentsArray = recipeJson.getJSONArray("comments");
                        List<Comments> commentList = new ArrayList<>();
                        for (int k = 0; k < commentsArray.length(); k++) {
                            JSONObject commentJson = commentsArray.getJSONObject(k);
                            Comments comment = new Comments();
                            comment.setText(commentJson.getString("text"));
                            comment.setRate(commentJson.getInt("rate"));
                            comment.setId(commentJson.getInt("comment_id"));
                            comment.setLogin(commentJson.getString("login"));
                            comment.setUsId(commentJson.getInt("usId"));
                            commentList.add(comment);
                        }

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
                        if(recipe.isAccepted==true){
                            recipe.setRecipeStringCategories(categoryList);
                            recipe.setStepsList(stepList);
                            recipe.setCommentsList(commentList);
                            recipeList.add(recipe);
                        }

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
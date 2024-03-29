package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Adapters.RecipeAdapterManagment;
import com.example.blogkulinarnymobileapp.Models.Comments;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.RecipeAdapter;
import com.example.blogkulinarnymobileapp.RecipeDetails;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageRecipesActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private RecyclerView recyclerView;
    private RecipeAdapterManagment adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        sessionManagement = SessionManagement.getInstance(ManageRecipesActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> recipeList = new ArrayList<>();


        LoadRecipeTask loadRecipeTask = new LoadRecipeTask();
        loadRecipeTask.execute(recipeList);

        adapter = new RecipeAdapterManagment(recipeList, ManageRecipesActivity.this, getApplicationContext());


        adapter.setOnItemClickListener(new RecipeAdapterManagment.OnItemClickListener() {
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
            public void onCommentButtonClick(Recipe recipe, Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.comment_dialog, null);
                builder.setView(dialogView);

                final EditText commentEditText = dialogView.findViewById(R.id.commentEditText);
                Button addButton = dialogView.findViewById(R.id.addButton);

                AlertDialog dialog = builder.create();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Comments comm = new Comments();
                        comm.setRecipeId(recipe.getId());

                        comm.setUsId(sessionManagement.getSessionId());
                        comm.setText(commentEditText.getText().toString());

                        onCommentAdded(comm);
                        dialog.dismiss();
                        Toast.makeText(ManageRecipesActivity.this, "Pomyślnie " +
                          "dodano komentarz", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }

            @Override
            public void onDeleteButtonClick(Recipe recipe) {
                DeleteRecipeTask dct = new DeleteRecipeTask();
                dct.execute(recipe.getId());
                recreate();
            }
        });

        recyclerView.setAdapter(adapter);
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
                            comment.setIsBlocked(commentJson.getInt("isBlocked"));
                            if(comment.getIsBlocked() == 1){
                                comment.setText(commentJson.getString("text"));
                                comment.setRate(commentJson.getInt("rate"));
                                comment.setId(commentJson.getInt("comment_id"));
                                comment.setLogin(commentJson.getString("login"));
                                comment.setUsId(commentJson.getInt("usId"));
                                commentList.add(comment);
                            }
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
                            recipe.setRecipeStringCategories(categoryList);
                            recipe.setStepsList(stepList);
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

    private class DeleteRecipeTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... recipeIds) {
            int recipeId = recipeIds[0];
            String url = "http://10.0.2.2:5000/delRecipeAdmin";

            try {
                // Tworzenie obiektu JSON z identyfikatorem komentarza
                JSONObject commentIdJson = new JSONObject();
                commentIdJson.put("recipe_id", recipeId);

                // Wysyłanie żądania HTTP do serwera
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(commentIdJson.toString());
                writer.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("DeleteRecipe", "Comment deleted successfully");
                }

                connection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    // dodawanie komentarzy
    public void onCommentAdded(Comments comment) {

        AddCommentTask act = new AddCommentTask();
        act.execute(comment);
        recreate();
    }

    private class AddCommentTask extends AsyncTask<Comments, Void, Void> {
        @Override
        protected Void doInBackground(Comments... comments) {
            Comments comment = comments[0];
            String url = "http://10.0.2.2:5000/addCommAdmin";

            try {
                // Tworzenie obiektu JSON z informacją o komentarzu
                JSONObject commentJson = new JSONObject();
                commentJson.put("text", comment.getText());
                commentJson.put("rate", 0);
                commentJson.put("recipe_id", comment.getRecipeId());
                commentJson.put("user_id", comment.getUsId());
                commentJson.put("isB", true);

                // Wysyłanie żądania HTTP do serwera
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(commentJson.toString());
                writer.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("AddComment", "Comment added successfully");
                }
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

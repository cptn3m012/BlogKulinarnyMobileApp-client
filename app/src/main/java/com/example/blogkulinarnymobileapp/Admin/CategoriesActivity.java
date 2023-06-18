package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.blogkulinarnymobileapp.Adapters.CategoryAdapter;
import com.example.blogkulinarnymobileapp.Models.Category;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.RecipeDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerView = findViewById(R.id.recyclerView);
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadCategoriesTask loadCategoriesTask = new LoadCategoriesTask();
        loadCategoriesTask.execute();
    }

    private class LoadCategoriesTask extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {
            String url = "http://10.0.2.2:5000/loadCategories";
            List<Category> categoryList = new ArrayList<>();

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

                    // Parsowanie odpowiedzi JSON i przetwarzanie kategorii
                    JSONObject responseJson = new JSONObject(response.toString());
                    JSONArray categoryArray = responseJson.getJSONArray("categories");
                    for (int i = 0; i < categoryArray.length(); i++) {
                        JSONObject categoryJson = categoryArray.getJSONObject(i);
                        int categoryId = categoryJson.getInt("id");
                        boolean isAccepted = categoryJson.getBoolean("isAccepted");
                        String categoryName = categoryJson.getString("name");

                        Category category = new Category(categoryId, isAccepted, categoryName);
                        categoryList.add(category);
                        Log.d("Category", "Category ID: " + categoryId + ", Is Accepted: " + isAccepted + ", Name: " + categoryName);
                    }
                }
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return categoryList;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            categoryList.clear();
            categoryList.addAll(categories);
            adapter.notifyDataSetChanged();
        }
    }
}

package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Adapters.CategoryAdapter;
import com.example.blogkulinarnymobileapp.Models.Category;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    private SessionManagement sessionManagement;
    private int rank, id;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList;

    private Button addCatBtn;

    private TextView categoryEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        addCatBtn = findViewById(R.id.addCategoryButton);
        categoryEditTxt = findViewById(R.id.categoryAddText);

        recyclerView = findViewById(R.id.recyclerView);
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList, this);
        adapter.setOnItemClickListener(this);

        sessionManagement = SessionManagement.getInstance(CategoriesActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadCategoriesTask loadCategoriesTask = new LoadCategoriesTask();
        loadCategoriesTask.execute();

        addCatBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String categoryName = categoryEditTxt.getText().toString();

                // Sprawdź, czy tekst zawiera tylko litery i spacje
                if (categoryName.matches("[a-zA-Z ]+")) {
                    AddCategoryTask addCt = new AddCategoryTask();
                    addCt.execute(categoryName);
                    recreate();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nazwa kategorii może zawierać tylko litery i spacje.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onLockButtonClick(Category category) {

        category.setAccepted(!category.isAccepted());
        adapter.notifyDataSetChanged();
        UpdateCategoryStateTask updst = new UpdateCategoryStateTask();
        updst.execute(category);
    }

    @Override
    public void onDeleteButtonClick(Category category) {

        DeleteCategoryTask dct = new DeleteCategoryTask();
        dct.execute(category);
        recreate();
    }

    // wczytywanie kategorii
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

    //zmianianie stanu kategorii
    private class UpdateCategoryStateTask extends AsyncTask<Category, Void, Void> {
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Category... categories) {
            Category category = categories[0];

            // Tutaj umieść kod aktualizacji stanu kategorii na serwerze
            String url = "http://10.0.2.2:5000/updateCategoryState";

            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Tworzenie obiektu JSON z danymi kategorii
                JSONObject categoryJson = new JSONObject();
                categoryJson.put("id", category.getId());
                categoryJson.put("isAccepted", category.isAccepted());
                categoryJson.put("name", category.getName());

                // Wysyłanie danych kategorii do serwera
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(categoryJson.toString());
                writer.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Aktualizacja stanu kategorii udana
                    Log.d("Update", "Category state updated successfully");

                }
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //usuwanie kategorii
    private class DeleteCategoryTask extends AsyncTask<Category, Void, Void> {
        @Override
        protected Void doInBackground(Category... categories) {
            Category category = categories[0];

            String url = "http://10.0.2.2:5000/deleteCategory/" + category.getId();

            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("DELETE");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Usunięcie kategorii udane
                    Log.d("Delete", "Category deleted successfully");

                    // Usuń kategorię z listy po usunięciu
                    categoryList.remove(category);
                    adapter.notifyDataSetChanged();
                }

                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    //dodawnaie kategorii
    private class AddCategoryTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... categoryNames) {
            String categoryName = categoryNames[0];

            String url = "http://10.0.2.2:5000/addCategory";

            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create JSON object with category details
                JSONObject categoryJson = new JSONObject();
                categoryJson.put("name", categoryName);

                // Write category JSON to request body
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write(categoryJson.toString());
                writer.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Category added successfully
                    Log.d("Add", "Category added successfully");

                    Category category = new Category(false,categoryName);
                    categoryList.add(category);
                    adapter.notifyDataSetChanged();
                }

                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}


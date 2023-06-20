package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Adapters.UserAdapter;
import com.example.blogkulinarnymobileapp.Models.User;
import com.example.blogkulinarnymobileapp.R;
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

public class ManageUsersActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {

    private SessionManagement sessionManagement;
    private int rank, id;
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManagement = SessionManagement.getInstance(ManageUsersActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        // Utwórz przykładową listę użytkowników
        List<User> userList = new ArrayList<>();

        LoadUserTask loadUserTask = new LoadUserTask();
        loadUserTask.execute(userList);

        adapter = new UserAdapter(userList, ManageUsersActivity.this);
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLockButtonClick(User user) {
        ManageUsersActivity.UpdateUserTask uut = new ManageUsersActivity.UpdateUserTask();
        uut.execute(user);
        recreate();
    }

    @Override
    public void onDeleteButtonClick(User user) {
        Toast.makeText(ManageUsersActivity.this, "Delete button clicked for user: " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    class LoadUserTask extends AsyncTask<List<User>, Void, List<User>> {

        @Override
        protected List<User> doInBackground(List<User>... params) {
            List<User> userList = params[0];
            String url = "http://10.0.2.2:5000/loadUsersToAccept";

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

                    // Parsowanie odpowiedzi JSON i tworzenie obiektów User
                    JSONObject responseJson = new JSONObject(response.toString());
                    JSONArray userArray = responseJson.getJSONArray("users");
                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject userJson = userArray.getJSONObject(i);
                        User user = new User();
                        user.setId(userJson.getInt("id"));
                        user.setAccepted(userJson.getBoolean("isAccepted"));
                        user.setImageURL(userJson.getString("imageURL"));
                        user.setLogin(userJson.getString("login"));
                        user.setMail(userJson.getString("mail"));
                        user.setRankInt(userJson.getInt("rank"));
                        userList.add(user);
                    }
                }
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }
            return userList;
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);
            adapter.notifyDataSetChanged();
        }
    }

    class UpdateUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            User user = users[0];

            try {
                // Tworzenie zapytania HTTP do wysłania zaktualizowanego użytkownika
                URL url = new URL("http://10.0.2.2:5000/updateUserAccept");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Tworzenie obiektu JSON z zaktualizowanymi danymi użytkownika
                JSONObject userJson = new JSONObject();
                userJson.put("id", user.getId());
                userJson.put("isAccepted", true);

                // Wysłanie danych użytkownika do serwera
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(userJson.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Odpowiedź serwera jest poprawna, można przetworzyć odpowiedź lub wykonać dodatkowe działania
                } else {
                    // Wystąpił błąd podczas wysyłania danych użytkownika
                }

                connection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Aktualizacja widoku po zaktualizowaniu użytkownika
            adapter.notifyDataSetChanged();
        }
    }
}

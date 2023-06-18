package com.example.blogkulinarnymobileapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.blogkulinarnymobileapp.Adapters.UserAdapter;
import com.example.blogkulinarnymobileapp.Models.User;
import com.example.blogkulinarnymobileapp.R;
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

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = findViewById(R.id.recyclerView);

        // Utwórz przykładową listę użytkowników
        List<User> userList = new ArrayList<>();

        LoadUserTask loadUserTask = new LoadUserTask();
        loadUserTask.execute(userList);

        adapter = new UserAdapter(userList, ManageUsersActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}

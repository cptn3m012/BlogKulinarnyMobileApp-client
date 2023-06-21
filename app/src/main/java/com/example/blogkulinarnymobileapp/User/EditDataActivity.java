package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditDataActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private EditText loginEditText;
    private EditText emailEditText;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        loginEditText = findViewById(R.id.loginEditText);
        emailEditText = findViewById(R.id.emailEditText);
        saveButton = findViewById(R.id.saveButton);

        sessionManagement = SessionManagement.getInstance(EditDataActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        // Pobierz wartości z sesji
        String login = sessionManagement.getSessionUsername();
        String email = sessionManagement.getSessionEmail();

        // Ustaw wartości pól edycji
        loginEditText.setText(login);
        emailEditText.setText(email);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pobierz nowe wartości z pól edycji
                String newLogin = loginEditText.getText().toString();
                String newEmail = emailEditText.getText().toString();

                // Wywołaj funkcję do zapisu danych w bazie danych
                updateUserData(newLogin, newEmail);
            }
        });
    }

    private void updateUserData(String login, String mail) {
        String url = "http://10.0.2.2:5000/update_user";

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("login", login);
            requestData.put("mail", mail);
            requestData.put("user_id", id);

            new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");

                        OutputStream outputStream = connection.getOutputStream();
                        outputStream.write(requestData.toString().getBytes());
                        outputStream.flush();
                        outputStream.close();

                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            return true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }

                @Override
                protected void onPostExecute(Boolean isUpdated) {
                    if (isUpdated) {
                        Toast.makeText(EditDataActivity.this, "Dane użytkownika zostały zaktualizowane.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditDataActivity.this, "Wystąpił błąd podczas aktualizacji danych użytkownika.", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import Home.HomeActivity;

public class DeleteAccountActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private EditText passwordEditText;
    private CheckBox confirmDeletionCheckBox;
    private Button deleteAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        passwordEditText = findViewById(R.id.passwordEditText);
        confirmDeletionCheckBox = findViewById(R.id.confirmDeletionCheckBox);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        sessionManagement = SessionManagement.getInstance(DeleteAccountActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                boolean isConfirmed = confirmDeletionCheckBox.isChecked();

                if (password.isEmpty()) {
                    Toast.makeText(DeleteAccountActivity.this, "Wprowadź hasło", Toast.LENGTH_SHORT).show();
                } else if (!isConfirmed) {
                    Toast.makeText(DeleteAccountActivity.this, "Potwierdź usunięcie konta", Toast.LENGTH_SHORT).show();
                } else {
                    // Wywołaj metodę do usuwania konta użytkownika
                    deleteUserAccount(password);
                }
            }
        });
    }

    private void deleteUserAccount(String password) {
        String url = "http://10.0.2.2:5000/delete_account";

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("user_id", id);
            requestData.put("password", password);

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
                protected void onPostExecute(Boolean isDeleted) {
                    if (isDeleted) {
                        // Zaloguj użytkownika
                        sessionManagement.logoutUser();
                        Toast.makeText(DeleteAccountActivity.this, "Twoje konto zostało usunięte.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeleteAccountActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        finish();
                    } else {
                        Toast.makeText(DeleteAccountActivity.this, "Wystąpił błąd podczas usuwania konta.", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
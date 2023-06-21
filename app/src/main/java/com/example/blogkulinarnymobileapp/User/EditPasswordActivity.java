package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;

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

public class EditPasswordActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private CheckBox showPasswordCheckBox;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        sessionManagement = SessionManagement.getInstance(EditPasswordActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        String password = sessionManagement.getSessionPassword();

        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPasswordEditText);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

                if (newPassword.equals(confirmNewPassword)) {
                    updatePassword(oldPassword, newPassword);
                } else {
                    Toast.makeText(EditPasswordActivity.this, "Nowe hasła nie są identyczne.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updatePassword(String oldPassword, String newPassword) {
        String url = "http://10.0.2.2:5000/update_password";

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("user_id", id);
            requestData.put("old_password", oldPassword);
            requestData.put("new_password", newPassword);

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
                        Toast.makeText(EditPasswordActivity.this, "Hasło zostało zaktualizowane.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPasswordActivity.this, "Wystąpił błąd podczas aktualizacji hasła.", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
package com.example.blogkulinarnymobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        passwordEditText.setContentDescription("Password");
        usernameEditText.setContentDescription("Login");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginOrEmail = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new LoginTask(MainActivity.this).execute(loginOrEmail, password);
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String loginOrEmail = params[0];
            String password = params[1];

            String url = "http://10.0.2.2:5000/login";
            String jsonInputString = "{\"username_or_email\": \"" + loginOrEmail + "\", \"password\": \"" + password + "\"}";

            try {
                URL loginUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonInputString.getBytes());
                outputStream.flush();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    connection.disconnect();

                    String jsonResponse = response.toString();
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    boolean success = jsonObject.getBoolean("success");
                    return success;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent("com.example.blogkulinarnymobileapp.ACTION_LOGIN_SUCCESS");
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

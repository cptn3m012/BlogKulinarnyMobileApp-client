package Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText loginEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private CheckBox acceptTermsCheckBox;
    private TextView errorTextView;
    private Button registerButton, loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginEditText = findViewById(R.id.loginEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        acceptTermsCheckBox = findViewById(R.id.acceptTermsCheckBox);
        errorTextView = findViewById(R.id.errorTextView);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                boolean acceptTerms = acceptTermsCheckBox.isChecked();

                if (validateRegistration(login, email, password, confirmPassword, acceptTerms)) {
                    RegisterTask registerTask = new RegisterTask(RegisterActivity.this);
                    registerTask.execute(login, email, password);
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateRegistration(String login, String email, String password, String confirmPassword, boolean acceptTerms) {
        if (login.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorTextView.setText("Wszystkie pola są wymagane.");
            return false;
        } else if (!password.equals(confirmPassword)) {
            errorTextView.setText("Hasła nie są identyczne.");
            return false;
        } else if (!acceptTerms) {
            errorTextView.setText("Musisz zaakceptować regulamin.");
            return false;
        }
        return true;
    }

    private class RegisterTask extends AsyncTask<String, Void, Boolean> {
        private Context context;

        public RegisterTask(Context context) {this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String login = params[0];
            String mail = params[1];
            String password = params[2];

            String url = "http://10.0.2.2:5000/auth/register";
            String jsonInputString = "{\"login\": \"" + login + "\", \"mail\": \"" + mail + "\", \"password\": \"" + password + "\"}";

            try {
                URL registerUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) registerUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonInputString.getBytes());
                outputStream.flush();

                int responseCode = connection.getResponseCode();
                connection.disconnect();

                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(context, "Rejestracja zakończona sukcesem", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(context, "Błąd podczas rejestracji", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
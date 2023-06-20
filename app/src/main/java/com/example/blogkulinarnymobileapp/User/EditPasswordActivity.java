package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

public class EditPasswordActivity extends AppCompatActivity {

    private SessionManagement sessionManagement;
    private int rank, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        sessionManagement = SessionManagement.getInstance(EditPasswordActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();
    }
}
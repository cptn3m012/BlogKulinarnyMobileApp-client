package com.example.blogkulinarnymobileapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blogkulinarnymobileapp.Adapters.GalleryAdapter;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;

import java.util.ArrayList;
import java.util.List;

public class EditUserActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {

    private SessionManagement sessionManagement;
    private int rank, id;
    private ImageView avatarImageView;
    private GalleryAdapter galleryAdapter;
    private Uri selectedImageUri;
    private List<Integer> avatarList;

    private TextView usernameTextView;
    private static final int REQUEST_IMAGE_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Inicjalizacja ImageView
        avatarImageView = findViewById(R.id.avatarImageView);

        // Inicjalizacja RecyclerView
        RecyclerView recyclerView = findViewById(R.id.galleryRecyclerView);

        // Ustawienie układu dla RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Inicjalizacja listy avatara
        avatarList = new ArrayList<>();
        avatarList.add(R.drawable.avatar0);
        avatarList.add(R.drawable.avatar10);
        avatarList.add(R.drawable.avatar11);
        avatarList.add(R.drawable.avatar12);
        avatarList.add(R.drawable.avatar2);
        avatarList.add(R.drawable.avatar3);
        avatarList.add(R.drawable.avatar4);
        avatarList.add(R.drawable.avatar6);
        avatarList.add(R.drawable.avatar8);
        avatarList.add(R.drawable.avatar9);

        sessionManagement = SessionManagement.getInstance(EditUserActivity.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        // Utworzenie i przypisanie adaptera galerii
        galleryAdapter = new GalleryAdapter(this, avatarList, this);
        recyclerView.setAdapter(galleryAdapter);

        usernameTextView = findViewById(R.id.usernameTextView); // Inicjalizacja TextView

        // Pobranie nazwy aktualnie zalogowanego użytkownika
        String currentUsername = getCurrentUsername();

        System.out.println("Current Username: " + currentUsername);
        // Ustawienie nazwy użytkownika w TextView
        usernameTextView.setText(currentUsername);
    }


    // Obsługa wyniku z galerii
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            avatarImageView.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onItemClick(int position) {
        int avatarResource = avatarList.get(position);
        avatarImageView.setImageResource(avatarResource);
    }

    // Metoda do pobrania nazwy aktualnie zalogowanego użytkownika
    private String getCurrentUsername() {
        return sessionManagement.getSessionUsername();
    }

    public void editProfileButtonClicked(View view) {
        // Przechodzenie do nowego activity po kliknięciu przycisku "Edytuj konto"
        Intent intent = new Intent(this, EditDataActivity.class);
        startActivity(intent);
    }

    public void changePasswordButtonClicked(View view) {
        // Przechodzenie do nowego activity po kliknięciu przycisku "Edytuj konto"
        Intent intent = new Intent(this, EditPasswordActivity.class);
        startActivity(intent);
    }

    public void deleteAccountButtonClicked(View view) {
        // Przechodzenie do nowego activity po kliknięciu przycisku "Edytuj konto"
        Intent intent = new Intent(this, DeleteAccountActivity.class);
        startActivity(intent);
    }

}
package com.example.blogkulinarnymobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blogkulinarnymobileapp.Adapters.CommentAdapter;
import com.example.blogkulinarnymobileapp.Adapters.StarAdapter;
import com.example.blogkulinarnymobileapp.Models.Comments;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Home.LoginActivity;
import Home.RegisterActivity;

public class RecipeDetails extends AppCompatActivity {
    Spinner rateStar;
    private Recipe recipeComment;
    private int rate, id, rank;

    private LinearLayout stepsLayout;
    private TextView titleTextView;
    private ImageView imageView;
    private TextView difficultyLabel;
    private TextView portionsTextView;
    private TextView avgTimeTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private EditText editTextCom;
    private RecyclerView stepsRecyclerView, comRecyclerView;
    private Button addCom;
    private SessionManagement sessionManagement;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        sessionManagement = SessionManagement.getInstance(RecipeDetails.this);
        rank = sessionManagement.getSession();
        id = sessionManagement.getSessionId();

        stepsLayout = findViewById(R.id.stepsLayout);
        titleTextView = findViewById(R.id.titleTextView);
        imageView = findViewById(R.id.imageView);
        difficultyLabel = findViewById(R.id.difficultyLabel);
        portionsTextView = findViewById(R.id.portionsTextView);
        avgTimeTextView = findViewById(R.id.avgTimeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);

        editTextCom = findViewById(R.id.editTextCom);
        rateStar = findViewById(R.id.rateStarSpinner);
        comRecyclerView = findViewById(R.id.comRecyclerView);
        addCom = findViewById(R.id.addCom);

        rateStar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rate = Integer.parseInt((String) rateStar.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.isNull(rank)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecipeDetails.this, "Nie jesteś zalogowany", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    RecipeDetails.CommentTask registerTask = new RecipeDetails.CommentTask(RecipeDetails.this);
                    //Tutaj potem zmienić id (9)
                    registerTask.execute(String.valueOf(rate), String.valueOf(recipeComment.id),
                            String.valueOf(editTextCom.getText()), String.valueOf(9));
                }
            }
        });

        //RegisterActivity.RegisterTask registerTask = new RegisterActivity.RegisterTask(RegisterActivity.this);
        //registerTask.execute(login, email, password);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("recipe")) {
            Recipe recipe = intent.getParcelableExtra("recipe");
            recipeComment = intent.getParcelableExtra("recipe");
            displayRecipeDetails(recipe);
            setupCommentsRecyclerView(recipe.getCommentsList());
            setupStepsRecyclerView(recipe.getStepsList());
        }

        String[] array2 = {"0", "1", "2", "3","4","5"};

        StarAdapter adapterStars = new StarAdapter(this, 1, array2);
        rateStar.setAdapter(adapterStars);
    }

    private void displayRecipeDetails(Recipe recipe) {
        titleTextView.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImageURL()).into(imageView);

        int difficulty = recipe.getDifficulty();
        if (difficulty == 1) {
            setDifficultyLabel("Łatwy", Color.rgb(0, 153, 0));
        } else if (difficulty == 2) {
            setDifficultyLabel("Średni", Color.rgb(255, 255, 0));
        } else if (difficulty == 3) {
            setDifficultyLabel("Trudny", Color.rgb(255, 102, 0));
        } else {
            setDifficultyLabel("Bardzo trudny", Color.rgb(255, 0, 0));
        }

        portionsTextView.setText(String.valueOf(recipe.getPortions()));
        avgTimeTextView.setText(recipe.getAvgTime() + "min");
        descriptionTextView.setText(recipe.getDescription());
    }

    private void setDifficultyLabel(String text, int color) {
        difficultyLabel.setText(text);
        difficultyLabel.setTextColor(color);
    }

    private void setupCommentsRecyclerView(List<Comments> commentsList){
        CommentAdapter commentAdapter = new CommentAdapter(commentsList);
        comRecyclerView.setAdapter(commentAdapter);
        comRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupStepsRecyclerView(List<RecipeElements> stepsList) {
        List<RecipeElements> stepListNew = new ArrayList<>();
        for (int i = 1; i < stepsList.size(); i++) {
            stepListNew.add(stepsList.get(i));
        }

        StepAdapter stepsAdapter = new StepAdapter(stepListNew);
        ingredientsTextView.setText(stepsList.get(0).description);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class CommentTask extends AsyncTask<String, Void, Boolean> {
        private final Context context;

        public CommentTask(Context context) {this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            int rate = Integer.parseInt(params[0]);
            int recipeId = Integer.parseInt(params[1]);
            String msg = params[2];
            int userId = Integer.parseInt(params[3]);

            String url = "http://10.0.2.2:5000/addUserComm";
            String jsonInputString = "{\"user_id\": \"" + userId + "\", \"rate\": \"" + rate + "\", \"text\": \"" + msg + "\", \"recipe_id\": \"" + recipeId + "\"}";

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
                Toast.makeText(context, "Udało się dodać komentarz", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Błąd podczas dodawania komentarza", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
package com.example.blogkulinarnymobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private OnItemClickListener onItemClickListener; // Dodaj deklarację interfejsu

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        //holder.userTextView.setText(String.valueOf(recipe.getUserFromRecipe().getId()));

        String imageUrl = recipe.getImageURL();
        new ImageLoaderTask(holder.imageView).execute(imageUrl);

        // Add click listener to recipe item layout
        // Add click listener to recipe item layout
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(recipe);
                }
            }
        });
        // Wygeneruj dynamicznie tagi
        /*List<RecipesCategory> tags = recipe.getRecipesCategories();
        for (RecipesCategory tag : tags) {
            TextView tagTextView = new TextView(holder.itemView.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 8, 0);
            tagTextView.setLayoutParams(layoutParams);
            //tagTextView.setBackgroundResource(R.drawable.tag_background); // Ustaw odpowiedni tło
            tagTextView.setText(tag.getCategory().getName());
            holder.tagsLayout.addView(tagTextView);
        }*/

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView userTextView;
        LinearLayout tagsLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            userTextView = itemView.findViewById(R.id.userTextView);
            tagsLayout = itemView.findViewById(R.id.tagsTextView);
        }
    }
}

class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public ImageLoaderTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];

        try {
            // Pobierz dane obrazu z linku
            String encodedImage = imageUrl.substring(imageUrl.indexOf(",") + 1);
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);

            // Dekoduj dane obrazu i utwórz obiekt Bitmap
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            // Wyświetl obraz w ImageView
            imageView.setImageBitmap(bitmap);
        }
    }
}

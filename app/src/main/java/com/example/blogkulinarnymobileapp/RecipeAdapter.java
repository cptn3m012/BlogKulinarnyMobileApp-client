package com.example.blogkulinarnymobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipesCategory;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;

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
        holder.userTextView.setText(String.valueOf(recipe.getUserFromRecipe().getId()));
        // Ustaw obrazek dla ImageView
        // holder.imageView.setImageResource(...);

        // Wygeneruj dynamicznie tagi
        List<RecipesCategory> tags = recipe.getRecipesCategories();
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
        }
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
            tagsLayout = itemView.findViewById(R.id.tagsLayout);
        }
    }
}

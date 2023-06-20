package com.example.blogkulinarnymobileapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.blogkulinarnymobileapp.Models.Recipe;
import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.example.blogkulinarnymobileapp.R;
import com.example.blogkulinarnymobileapp.SessionManagement.SessionManagement;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    Activity activity;
    private List<Recipe> recipeList;
    private OnItemClickListener onItemClickListener;
    private SessionManagement sessionManagement;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
        void onLockButtonClick(Recipe recipe);
        void onCommentButtonClick(Recipe recipe, Context context);
        void onDeleteButtonClick(Recipe recipe);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RecipeAdapter(List<Recipe> recipeList, Activity activity) {
        this.recipeList = recipeList;
        this.activity = activity;
    }

    public void setFilteredList(List<Recipe> filteredList){
        this.recipeList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_managment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());

        if(recipe.isAccepted != true){
            holder.lockBtn.setText("ODBLOKUJ");
        }

        new ImageLoaderTask(holder.imageView, recipe.getImageURL()).execute(recipe.getImageURL());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(recipe);
                }
            }
        });

        if (holder.rank == 2) {
            holder.lockBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onLockButtonClick(recipe);
                    }
                }
            });

            holder.commBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onCommentButtonClick(recipe, v.getContext());
                    }
                }
            });

            holder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onDeleteButtonClick(recipe);
                    }
                }
            });
        } else {
            holder.lockBtn.setVisibility(View.GONE);
            holder.commBtn.setVisibility(View.GONE);
            holder.delBtn.setVisibility(View.GONE);
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

        int rank = 2;
        private Button lockBtn, commBtn, delBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            userTextView = itemView.findViewById(R.id.userTextView);
            tagsLayout = itemView.findViewById(R.id.tagsTextView);

            lockBtn = itemView.findViewById(R.id.lock_btn);
            commBtn = itemView.findViewById(R.id.comm_btn);
            delBtn = itemView.findViewById(R.id.del_btn);
        }
    }
}

class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private List<RecipeElements> stepList;
    private Context context;

    public StepAdapter(List<RecipeElements> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeElements step = stepList.get(position);
        holder.bindStep(step);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView numOfListTextView;
        private TextView descriptionTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numOfListTextView = itemView.findViewById(R.id.numOfListTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bindStep(RecipeElements step) {
            if (step.getNoOfList() != 0){
                numOfListTextView.setText(String.valueOf(step.getNoOfList()) + ".");
                descriptionTextView.setText(step.getDescription());
                if(step.getNoOfList() != 0){
                    Picasso.get().load(step.getImageURL()).into(imageView);
                }
            }
        }
    }
}

class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private String urlImage;

    public ImageLoaderTask(ImageView imageView, String urlImage) {
        this.imageView = imageView;
        this.urlImage = urlImage;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return Picasso.get()
                    .load(urlImage)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmapResult) {
        if (bitmapResult != null) {
            imageView.setImageBitmap(bitmapResult);
        }
    }
}





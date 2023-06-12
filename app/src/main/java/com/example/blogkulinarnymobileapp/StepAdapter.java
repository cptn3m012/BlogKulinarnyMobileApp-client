package com.example.blogkulinarnymobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.Models.RecipeElements;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
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
            numOfListTextView.setText(String.valueOf(step.getNoOfList()));
            descriptionTextView.setText(step.getDescription());

            Picasso.get().load(step.getImageURL()).into(imageView);
        }
    }
}

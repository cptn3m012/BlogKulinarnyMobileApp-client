package com.example.blogkulinarnymobileapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.Models.Category;
import com.example.blogkulinarnymobileapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categoryList;
    private OnItemClickListener listener;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.loginTextView.setText(category.getName());

        if (category.isAccepted()) {
            // Kategoria jest odblokowana
            holder.lockButton.setText("Odblokowane");
        } else {
            // Kategoria jest zablokowana
            holder.lockButton.setText("Zablokowane");
        }

        holder.lockButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLockButtonClick(category);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteButtonClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loginTextView;
        Button lockButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loginTextView = itemView.findViewById(R.id.loginTextView);
            lockButton = itemView.findViewById(R.id.lock_btn);
            deleteButton = itemView.findViewById(R.id.del_btn);
        }
    }


    // zarzadzanie przyciskami
    public interface OnItemClickListener {
        void onLockButtonClick(Category category);

        void onDeleteButtonClick(Category category);
    }
}

package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
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
    private Context context;
    private OnItemClickListener listener;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
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

        holder.lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLockButtonClick(category);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteButtonClick(category);
                }
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

    public interface OnItemClickListener {
        void onLockButtonClick(Category category);

        void onDeleteButtonClick(Category category);
    }
}

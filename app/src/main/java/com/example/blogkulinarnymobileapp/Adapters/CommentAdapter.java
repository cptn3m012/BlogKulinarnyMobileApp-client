package com.example.blogkulinarnymobileapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comAuthor, comment, rate;
        Button comDeleteBTN;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comAuthor = itemView.findViewById(R.id.comAuthor);
            comment = itemView.findViewById(R.id.comment);
            rate = itemView.findViewById(R.id.rate);
            comDeleteBTN = itemView.findViewById(R.id.comDeleteBTN);
        }
    }
}

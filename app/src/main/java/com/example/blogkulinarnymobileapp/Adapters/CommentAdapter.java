package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.Models.Comments;
import com.example.blogkulinarnymobileapp.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    List<Comments> commentsList;
    public int id;
    private OnCommentDeleteListener commentDeleteListener;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommentAdapter(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    public void updateComments(List<Comments> commentsList) {
        this.commentsList = commentsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public interface OnCommentDeleteListener {
        void onCommentDelete(int id, int usId);
    }

    public void setOnCommentDeleteListener(OnCommentDeleteListener listener) {
        commentDeleteListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments comments = commentsList.get(position);
        holder.comment.setText(comments.getText());
        holder.rate.setText(String.valueOf(comments.getRate()));
        holder.comAuthor.setText(comments.getLogin());


        holder.comDeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentDeleteListener != null) {
                    commentDeleteListener.onCommentDelete(comments.getId(), comments.getUsId());
                }
            }
        });
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

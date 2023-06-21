package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.Models.User;
import com.example.blogkulinarnymobileapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> userList;
    private OnItemClickListener listener;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bindLogin(user);
        holder.lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLockButtonClick(user);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteButtonClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView loginTextView;
        private final Button lockButton;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loginTextView = itemView.findViewById(R.id.loginTextView);
            lockButton = itemView.findViewById(R.id.lock_btn);
            deleteButton = itemView.findViewById(R.id.del_btn);
        }

        public void bindLogin(User user) {
            loginTextView.setText(user.getLogin());
        }
    }

    public interface OnItemClickListener {
        void onLockButtonClick(User user);
        void onDeleteButtonClick(User user);
    }
}

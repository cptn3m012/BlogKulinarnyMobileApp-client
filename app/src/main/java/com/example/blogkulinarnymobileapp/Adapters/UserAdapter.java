package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.blogkulinarnymobileapp.Models.User;
import com.example.blogkulinarnymobileapp.R;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private OnItemClickListener listener;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_tile, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bindLogin(user);
        holder.bindMail(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
        void onLockButtonClick(User user);
        void onDeleteButtonClick(User user);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView loginTextView;

        private TextView mailTextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            loginTextView = itemView.findViewById(R.id.loginTextView);
            mailTextView = itemView.findViewById(R.id.mailTextView);
            itemView.setOnClickListener(this);
        }

        public void bindLogin(User user) {
            loginTextView.setText(user.getLogin());
        }

        public void bindMail(User user) {
            mailTextView.setText(user.getMail());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                User user = userList.get(position);
                if (listener != null) {
                    listener.onItemClick(user);
                }
            }
        }
    }
}

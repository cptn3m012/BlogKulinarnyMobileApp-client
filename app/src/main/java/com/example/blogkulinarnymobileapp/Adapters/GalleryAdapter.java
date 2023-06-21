package com.example.blogkulinarnymobileapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> avatarList;
    private final OnItemClickListener itemClickListener;
    private int selectedAvatarPosition;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public GalleryAdapter(Context context, List<Integer> avatarList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.avatarList = avatarList;
        this.itemClickListener = itemClickListener;
        this.selectedAvatarPosition = -1; // Inicjalizacja wartości początkowej
    }

    public void setSelectedAvatarPosition(int position) {
        selectedAvatarPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int avatarResource = avatarList.get(position);
        holder.avatarImageView.setImageResource(avatarResource);

        // Sprawdź, czy to jest wybrane zdjęcie
        if (position == selectedAvatarPosition) {
            // Jeśli tak, ustaw obramowanie lub inny efekt wizualny
            holder.avatarImageView.setBackgroundResource(R.drawable.selected_border);
        } else {
            // W przeciwnym razie usuń efekt wizualny
            holder.avatarImageView.setBackgroundResource(0);
        }
    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
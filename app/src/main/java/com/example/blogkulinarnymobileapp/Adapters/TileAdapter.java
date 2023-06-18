package com.example.blogkulinarnymobileapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogkulinarnymobileapp.R;

import java.util.List;

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.TileViewHolder> {
    private List<TileData> data;

    private OnItemClickListener onItemClickListener;

    public TileAdapter(List<TileData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, parent, false);
        return new TileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TileViewHolder holder, int position) {
        TileData item = data.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descTextView.setText(item.getDesc());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TileViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descTextView;

        public TileViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descTextView = itemView.findViewById(R.id.descTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

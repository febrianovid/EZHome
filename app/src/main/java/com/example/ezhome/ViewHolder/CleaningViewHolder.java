package com.example.ezhome.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezhome.Interface.ItemClickListener;
import com.example.ezhome.R;

public class CleaningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cleaning_name;
    public ImageView cleaning_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CleaningViewHolder(View itemView) {
        super(itemView);

        cleaning_name = (TextView)itemView.findViewById(R.id.cleaning_name);
        cleaning_image = (ImageView)itemView.findViewById(R.id.cleaning_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}

package com.example.selfbook.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.R;

public class bookCoverViewHolder extends RecyclerView.ViewHolder {

    public ImageView bookImage;
    public TextView bookDescription;
    public bookCoverViewHolder(@NonNull View itemView) {
        super(itemView);

        bookImage = itemView.findViewById(R.id.iv_gideBook);
        bookDescription = itemView.findViewById(R.id.tv_guideBookPrice);
    }
}

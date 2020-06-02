package com.example.selfbook.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.R;

public class guideBookViewHolder extends RecyclerView.ViewHolder {

    public ImageView guideBookImage;
    public TextView guideBookPrice;
    public guideBookViewHolder(@NonNull View itemView) {
        super(itemView);

        guideBookImage = itemView.findViewById(R.id.iv_gideBook);
        guideBookPrice = itemView.findViewById(R.id.tv_guideBookPrice);
    }
}

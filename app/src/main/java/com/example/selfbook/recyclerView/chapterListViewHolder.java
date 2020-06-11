package com.example.selfbook.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.R;

public class chapterListViewHolder extends RecyclerView.ViewHolder{

    public ImageView draftImage;
    public TextView draftNumber;
    public chapterListViewHolder(@NonNull View itemView) {
        super(itemView);

        draftImage = itemView.findViewById(R.id.iv_chapter);
        draftNumber = itemView.findViewById(R.id.tv_chapter);


    }

}

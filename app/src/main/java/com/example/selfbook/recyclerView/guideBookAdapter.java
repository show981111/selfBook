package com.example.selfbook.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.R;
import com.example.selfbook.templateInfo;

import java.util.ArrayList;

public class guideBookAdapter extends RecyclerView.Adapter<guideBookViewHolder> {

    private Context mContext;
    private ArrayList<templateInfo> templateInfos;
    public guideBookAdapter(Context context, ArrayList<templateInfo> templateInfos) {
        mContext = context;
        this.templateInfos = templateInfos;
    }

    @NonNull
    @Override
    public guideBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View baseView = View.inflate(mContext, R.layout.guidebook_item,null);
        guideBookViewHolder guideBookViewHolder = new guideBookViewHolder(baseView);
        return guideBookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull guideBookViewHolder holder, int position) {
        templateInfo templateInfoItem = templateInfos.get(position);

        holder.guideBookPrice.setText(String.valueOf(templateInfoItem.getBookPrice()) + "원");
    }

    @Override
    public int getItemCount() {
        return templateInfos.size();
    }
}

package com.example.selfbook.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.R;

import java.util.ArrayList;

public class userAnswerAdapter extends RecyclerView.Adapter<userAnswerViewHolder> {

    private Context mContext;
    private ArrayList<userAnswer> userAnswerArrayList = new ArrayList<>();

    @NonNull
    @Override
    public userAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.chapter,null);
        userAnswerViewHolder userAnswerViewHolder = new userAnswerViewHolder(baseView);
        return userAnswerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull userAnswerViewHolder holder, int position) {
        if(userAnswerArrayList != null)
        {
            holder.draftNumber.setText(position);

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

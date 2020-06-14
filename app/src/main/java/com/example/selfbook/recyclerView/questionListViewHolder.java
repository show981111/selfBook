package com.example.selfbook.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.R;

public class questionListViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_question;
    public LinearLayout ll_dropDown;
    public LinearLayout ll_typeAnswer;
    public ImageView iv_dropDown;
    public ImageButton bt_uploadAnswer;
    public EditText et_typeAnswer;
    public LinearLayout ll_questionItem;
    public questionListViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_question = itemView.findViewById(R.id.tv_question);
        ll_dropDown = itemView.findViewById(R.id.ll_dropDown);
        iv_dropDown = itemView.findViewById(R.id.iv_dropDown);
        bt_uploadAnswer = itemView.findViewById(R.id.bt_uploadAnswer);
        et_typeAnswer = itemView.findViewById(R.id.et_typeAnswer);
        ll_typeAnswer = itemView.findViewById(R.id.ll_typeAnswer);
        ll_questionItem = itemView.findViewById(R.id.ll_questionItem);
        ll_typeAnswer.setVisibility(View.GONE);

    }

    public EditText getEt_typeAnswer() {
        return et_typeAnswer;
    }
}

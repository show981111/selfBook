package com.example.selfbook.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.R;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.postUserAnswer;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.selfbook.MainActivity.userID;


public class questionListAdapter extends RecyclerView.Adapter<questionListViewHolder> {

    private Context mContext;
    private templateTreeNode templateTree;
    private Activity activity;

    private ArrayList<userAnswer> questionList;

    public questionListAdapter(Context mContext, ArrayList<userAnswer> questionList) {
        this.mContext = mContext;
        this.questionList = questionList;
        activity = (Activity) mContext;

    }

    @NonNull
    @Override
    public questionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.question,null);
        questionListViewHolder questionListViewHolder = new questionListViewHolder(baseView);
        return questionListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final questionListViewHolder holder, final int position) {
        if(questionList != null){

//            tv_question = itemView.findViewById(R.id.tv_question);
//            ll_dropDown = itemView.findViewById(R.id.ll_dropDown);
//            iv_dropDown = itemView.findViewById(R.id.iv_dropDown);
//            bt_uploadAnswer = itemView.findViewById(R.id.bt_uploadAnswer);
//            et_typeAnswer = itemView.findViewById(R.id.et_typeAnswer);
            holder.tv_question.setText(questionList.get(position).getName());
            final String prevAnswer = questionList.get(position).getAnswer();

            if(questionList.get(position).getAnswer() != null && !TextUtils.isEmpty(questionList.get(position).getAnswer())
                    && !questionList.get(position).getAnswer().equals("null")){
                holder.et_typeAnswer.setText(questionList.get(position).getAnswer());
                holder.ll_questionItem.setBackgroundColor(Color.rgb(190, 185, 201));
            }
            if(questionList.get(position).getHint() != null && !TextUtils.isEmpty(questionList.get(position).getHint())){
                holder.et_typeAnswer.setHint(questionList.get(position).getHint());
            }


            if(TextUtils.isEmpty(questionList.get(position).getHint()) &&
                    !questionList.get(position).getHint().equals("null"))
            {
                holder.et_typeAnswer.setHint(questionList.get(position).getHint());
            }


            holder.ll_dropDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.ll_typeAnswer.getVisibility() == View.GONE) {
                        holder.ll_typeAnswer.setVisibility(View.VISIBLE);
                        holder.iv_dropDown.setImageResource(R.drawable.ic_arrowup);
                    }else{
                        holder.ll_typeAnswer.setVisibility(View.GONE);
                        holder.iv_dropDown.setImageResource(R.drawable.ic_dropdown);
                    }
                }
            });

            holder.bt_uploadAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    //Context context, int key, String input, String userID, EditText et_title, String from
                    if(userID != null && !TextUtils.isEmpty(userID) && !prevAnswer.equals(holder.et_typeAnswer.getText().toString()) &&
                            !TextUtils.isEmpty(holder.et_typeAnswer.getText().toString())) {
                        postUserAnswer postUserAnswer = new postUserAnswer(mContext, questionList.get(position).getID(), holder.et_typeAnswer.getText().toString()
                                , userID, holder.et_typeAnswer, "question");
                        postUserAnswer.execute(Api.POST_SETUSERANSWER);
                    }else if(userID == null || TextUtils.isEmpty(userID) ){
                        Toast toast = Toast.makeText(mContext,"먼저 로그인을 해주세요!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show(); // center align
                    }else if (TextUtils.isEmpty(holder.et_typeAnswer.getText().toString())){
                        Toast toast = Toast.makeText(mContext,"답변을 입력해주세요!",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {

                    }
                }
            });

            holder.et_typeAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.et_typeAnswer.setBackgroundColor(Color.WHITE);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }
}

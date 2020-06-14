package com.example.selfbook.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.QuestionActivity;
import com.example.selfbook.R;

import java.util.ArrayList;
import java.util.List;

public class chapterListAdapter extends RecyclerView.Adapter<chapterListViewHolder> {

    private Context mContext;
    private templateTreeNode templateTree;

    private List<templateTreeNode> chapterList;
    private ArrayList<userAnswer> questionArrayList = new ArrayList<>();
    public chapterListAdapter(Context mContext, templateTreeNode templateTree) {
        this.mContext = mContext;
        this.templateTree = templateTree;
        chapterList = templateTree.getChildren();
        Log.d("chapter", chapterList.size() + "size");
    }

    @NonNull
    @Override
    public chapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mContext, R.layout.chapter,null);
        chapterListViewHolder userAnswerViewHolder = new chapterListViewHolder(baseView);
        return userAnswerViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull chapterListViewHolder holder, final int position) {
        if(templateTree != null)
        {
            int chapnum = position + 1;

            Log.d("chapter", ""+chapnum);
            //holder.bookDescription.setText(String.valueOf(templateItem.getBookPrice()) + "원");
            holder.draftNumber.setText("CH."+chapnum);
            if(chapterList.get(position).getData().getHint() != null)
            {
                if(chapterList.get(position).getData().getHint().equals("full")){
                    //holder.draftImage.setColorFilter(Color.BLACK);
                    holder.draftImage.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.colorAccent));

                }
            }


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionArrayList.clear();
                List<templateTreeNode> questionList = chapterList.get(position).getChildren();
                for(int i = 0; i < questionList.size(); i++)
                {
                    questionArrayList.add(questionList.get(i).getData());
                }
                Intent intent = new Intent(mContext, QuestionActivity.class);
                intent.putParcelableArrayListExtra("questionArray", questionArrayList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
}

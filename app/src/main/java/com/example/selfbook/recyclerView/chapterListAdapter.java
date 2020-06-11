package com.example.selfbook.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.R;

import java.util.ArrayList;
import java.util.List;

public class chapterListAdapter extends RecyclerView.Adapter<chapterListViewHolder> {

    private Context mContext;
    private templateTreeNode templateTree;

    private List<templateTreeNode> chapterList;
    private ArrayList<userAnswer> chapterArrayList = new ArrayList<>();
    public chapterListAdapter(Context mContext, templateTreeNode templateTree) {
        this.mContext = mContext;
        this.templateTree = templateTree;
        if(this.templateTree == null)
        {
            Log.d("chapter", "NUll??");
        }else{
            Log.d("chapter", "notNUll??");
        }
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

    @Override
    public void onBindViewHolder(@NonNull chapterListViewHolder holder, int position) {
        if(templateTree != null)
        {
            int chapnum = position + 1;

            Log.d("chapter", ""+chapnum);
            //holder.bookDescription.setText(String.valueOf(templateItem.getBookPrice()) + "원");
            holder.draftNumber.setText("CH."+chapnum);
//            if(chapterList.get(position).getData().getHint().equals("full")){
//                holder.draftImage.setColorFilter(Color.BLACK);
//            }

        }
        Log.d("chapter", "NULL");
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
}

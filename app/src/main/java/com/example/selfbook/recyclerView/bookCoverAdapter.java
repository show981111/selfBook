package com.example.selfbook.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.Data.userInfo;
import com.example.selfbook.Data.viewBook;
import com.example.selfbook.R;

import java.util.ArrayList;

public class bookCoverAdapter<T extends viewBook> extends RecyclerView.Adapter<bookCoverViewHolder> {

    private T type;
    private Context mContext;
    private ArrayList<T> itemInfos;

    private ArrayList<templateInfo> templateInfos = new ArrayList<>();
    private ArrayList<userInfo> userPurchasesArrayList = new ArrayList<>();
    public bookCoverAdapter(Context context, ArrayList<T> itemInfos) {
        mContext = context;
        this.itemInfos = itemInfos;
    }

    public void set(T t) {
        type = t;
    }

    public T get() {
        return type;
    }
    @NonNull
    @Override
    public bookCoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View baseView = View.inflate(mContext, R.layout.guidebook_item,null);
        bookCoverViewHolder guideBookViewHolder = new bookCoverViewHolder(baseView);
        return guideBookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull bookCoverViewHolder holder, int position) {
        if(!itemInfos.isEmpty()) {
            if (itemInfos.get(0) instanceof templateInfo) {
                templateInfos = (ArrayList<templateInfo>) itemInfos;
                templateInfo templateItem = templateInfos.get(position);
                Log.d("fetchGuideBookBind", String.valueOf(templateItem.getBookPrice()));
                holder.bookDescription.setText(String.valueOf(templateItem.getBookPrice()) + "원");
            }

            if (itemInfos.get(0) instanceof userInfo) {
                userPurchasesArrayList = (ArrayList<userInfo>) itemInfos;
                userInfo userPurchaseItem = userPurchasesArrayList.get(position);
                holder.bookDescription.setText(userPurchaseItem.getUserTemplateCode());
                //holder.bookDescription.setText(userPurchaseItem.getStatus());
            }
        }

    }

    @Override
    public int getItemCount() {
        return itemInfos.size();
    }
}

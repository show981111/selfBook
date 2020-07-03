package com.example.selfbook.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.selfbook.BasicDraftInfoFragment;
import com.example.selfbook.BookInfoActivity;
import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.Data.userInfo;
import com.example.selfbook.Data.viewBook;
import com.example.selfbook.MyDraftActivity;
import com.example.selfbook.R;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchTemplateContent;

import java.util.ArrayList;

public class bookCoverAdapter<T extends viewBook> extends RecyclerView.Adapter<bookCoverViewHolder> {

    private T type;
    private Context mContext;
    private ArrayList<T> itemInfos;

    private ArrayList<templateInfo> templateInfos = new ArrayList<>();
    private templateInfo templateItem;
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
    public void onBindViewHolder(@NonNull bookCoverViewHolder holder, final int position) {
        if(!itemInfos.isEmpty()) {
            if (itemInfos.get(0) instanceof templateInfo) {
                templateInfos = (ArrayList<templateInfo>) itemInfos;
                templateItem = templateInfos.get(position);
                Log.d("fetchGuideBookBind", String.valueOf(templateItem.getBookPrice()));
                holder.bookDescription.setText(String.valueOf(templateItem.getBookPrice()) + "원");
                Glide.with(mContext).load(Api.GET_IMAGEBASEURL+templateItem.getBookCover()).into(holder.bookImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onBind","clicked");
                        Intent intent = new Intent(mContext, BookInfoActivity.class);
                        intent.putExtra("templateInfo", templateInfos.get(position));
                        mContext.startActivity(intent);
                        //Toast.makeText(mContext, "TOUCHED"+String.valueOf(templateItem.getBookPrice()), Toast.LENGTH_LONG).show();
                    }
                });

            }

            if (itemInfos.get(0) instanceof userInfo) {
                userPurchasesArrayList = (ArrayList<userInfo>) itemInfos;
                userInfo userPurchaseItem = userPurchasesArrayList.get(position);
                if(userPurchaseItem.getUserTemplateCode() == 0 )
                {
                    Log.d("login","mydraft");
                    holder.itemView.setVisibility(View.GONE);
                    return;
                }
                holder.bookDescription.setText(String.valueOf(userPurchaseItem.getUserTemplateCode()));
                Glide.with(mContext).load(Api.GET_IMAGEBASEURL+userPurchaseItem.getUserBookCover()).into(holder.bookImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onBind","clicked");
                        Intent intent = new Intent(mContext, MyDraftActivity.class);
                        Log.d("login",userPurchasesArrayList.get(position).getUserBookName());
                        intent.putExtra("userPurchaseInfo", userPurchasesArrayList.get(position));
                        mContext.startActivity(intent);

                    }
                });
                //holder.bookDescription.setText(userPurchaseItem.getStatus());
            }
        }

    }

    @Override
    public int getItemCount() {
        return itemInfos.size();
    }
}

package com.example.selfbook.recyclerView;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.Content;
import com.example.selfbook.R;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchDelegateList;

import java.util.ArrayList;
import java.util.List;

import static com.example.selfbook.MainActivity.userID;

public class chapterListAdapter extends RecyclerView.Adapter<chapterListViewHolder> {

    private Context mContext;
    private templateTreeNode templateTree;

    private List<templateTreeNode> chapterList;
    private ArrayList<Content> delegateArrayList = new ArrayList<>();
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
            if(chapterList.get(position).getData().getStatus() == 1){
                //holder.draftImage.setColorFilter(Color.BLACK);
                holder.draftImage.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.warmColor));

            }


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegateArrayList.clear();
                Log.d("getDelegate",String.valueOf(chapterList.get(position).getData().getID()));
                fetchDelegateList fetchDelegateList = new fetchDelegateList(userID, templateTree.getData().getID(),
                        chapterList.get(position).getData().getID(),mContext, chapterList.get(position) );
                fetchDelegateList.execute(Api.GET_DELEGATELIST);


                //String userID, int templateCode, int chapterCode, Context mContext, templateTreeNode chapterNode
//                List<templateTreeNode> delegateList = chapterList.get(position).getChildren();
//                for(int i = 0; i < delegateList.size(); i++)
//                {
//                    delegateArrayList.add(delegateList.get(i).getData());
//                }
//                Intent intent = new Intent(mContext, QuestionActivity.class);
//                intent.putExtra("templateCode",templateTree.getData().getID());
////                intent.putParcelableArrayListExtra("delegateArray", delegateArrayList);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
}

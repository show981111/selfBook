package com.example.selfbook.getData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.Content;
import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.DelegateActivity;
import com.example.selfbook.DetailActivity;
import com.example.selfbook.recyclerView.chapterListAdapter;
import com.example.selfbook.recyclerView.delegateListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class fetchDetailList extends AsyncTask<String, Void, ArrayList<Content> > {

    private String userID;
    private int delegateCode;
    private Context mContext;
    private int templateCode;
    private int pos;
    private String title;
    //private RecyclerView rv_detail;

    //public templateTreeNode delegateNode;

    private ArrayList<Content> detailList = new ArrayList<>();

    public fetchDetailList(String userID, int delegateCode, Context mContext, int templateCode, int pos, String title) {
        this.userID = userID;
        this.delegateCode = delegateCode;
        this.mContext = mContext;
        this.templateCode = templateCode;
        this.pos = pos;
        this.title = title;
        Log.d("fetchDetailList",mContext.toString());
    }

    @Override
    protected ArrayList<Content> doInBackground(String... strings) {
        String url = strings[0];
        if(TextUtils.isEmpty(userID) || delegateCode == 0){
            return null;
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .add("delegateCode", String.valueOf(delegateCode))
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response responses = null;

        try {
            responses = okHttpClient.newCall(request).execute();
            Log.d("fetchDetailList", "res");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchDetailList", "IOE");
            return null;
        }

        String jsonData = null;
        try {

            jsonData = responses.body().string();
            JSONArray detailArray = new JSONArray(jsonData);

            for (int i = 0; i < detailArray.length(); i++) {
                JSONObject detailObject = detailArray.getJSONObject(i);
                int detailCode = detailObject.getInt("detailCode");
                String detailName = detailObject.getString("detailName");
                String detailHint = detailObject.getString("detailHint");
                String detailAnswer = detailObject.getString("detailAnswer");
                int status = detailObject.getInt("status");
                Content content = new Content(detailCode, detailName, detailHint, detailAnswer, status);
//                templateTreeNode detailNode = new templateTreeNode(content);
                detailList.add(content);

            }

            return detailList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Content> detailList) {
        super.onPostExecute(detailList);
        if(detailList == null){
            return;
        }
//        delegateListAdapter myDetailAdapter = new delegateListAdapter(mContext, detailList);
//        Log.d("chapAdater", "CALLED");
//        myDetailAdapter.notifyDataSetChanged();
//        rv_detail.setLayoutManager(new GridLayoutManager(mContext, 3));
//        rv_detail.setAdapter(myDetailAdapter);
        Intent intent1 = new Intent(mContext, DetailActivity.class);
        intent1.putExtra("templateCode",templateCode);
        intent1.putParcelableArrayListExtra("detailList",detailList );
        intent1.putExtra("title",title);
        intent1.putExtra("pos",pos);
        mContext.startActivity(intent1.addFlags(FLAG_ACTIVITY_NEW_TASK));
    }
}

package com.example.selfbook.getData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.selfbook.Data.Content;
import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.DelegateActivity;

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

public class fetchDelegateList extends AsyncTask<String, Void, ArrayList<Content> > {

    private String userID;
    private int templateCode;
    private int chapterCode;
    private Context mContext;
    private int chapterNum;
    public templateTreeNode chapterNode;

    private ArrayList<Content> delegateArrayList = new ArrayList<>();



    public fetchDelegateList(String userID, int templateCode, int chapterCode, Context mContext, templateTreeNode chapterNode, int chapterNum) {
        this.userID = userID;
        this.templateCode = templateCode;
        this.chapterCode = chapterCode;
        this.mContext = mContext;
        this.chapterNode = chapterNode;
        this.chapterNum = chapterNum;
        Log.d("fetchDelegateList", userID);
        Log.d("fetchDelegateList", String.valueOf(chapterCode));
    }

    @Override
    protected ArrayList<Content> doInBackground(String... strings) {
        String url = strings[0];
        if(TextUtils.isEmpty(userID) || chapterCode == 0){
            return null;
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .add("chapterCode", String.valueOf(chapterCode))
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response responses = null;

        try {
            responses = okHttpClient.newCall(request).execute();
            Log.d("fetchDelegateList", "res");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchDelegateList", "IOE");
            return null;
        }

        String jsonData = null;

        try {
            jsonData = responses.body().string();
            //Log.d("fetchDelegateList", jsonData);
            JSONArray delegateArray = new JSONArray(jsonData);
            delegateArrayList.clear();
            for(int i = 0; i < delegateArray.length(); i++)
            {
                JSONObject delegateObject = delegateArray.getJSONObject(i);
                int delegateCode = delegateObject.getInt("delegateCode");
                String delegateName =delegateObject.getString("delegateName");
                String delegateHint =delegateObject.getString("delegateHint");
                String delegateAnswer =delegateObject.getString("delegateAnswer");
                int status = delegateObject.getInt("status");
                Content content = new Content(delegateCode, delegateName,delegateHint, delegateAnswer ,status);
                templateTreeNode delegate = new templateTreeNode(content);
                delegateArrayList.add(content);
                chapterNode.addChild(delegate);

            }
            return delegateArrayList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Content> delegateArrayList) {
        super.onPostExecute(delegateArrayList);

        if(delegateArrayList == null){
            return;
        }
        Intent intent = new Intent(mContext, DelegateActivity.class);
        intent.putExtra("templateCode",templateCode);
        intent.putExtra("chapterNum",chapterNum);
        intent.putExtra("chapterName",chapterNode.getData().getName());
        intent.putParcelableArrayListExtra("delegateArray",delegateArrayList );
        mContext.startActivity(intent);
    }
}

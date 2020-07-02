package com.example.selfbook.getData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.Content;
import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.recyclerView.chapterListAdapter;

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

public class fetchChapterList extends AsyncTask<String, Void, templateTreeNode> {

    private String userID;
    private int templateCode;
    private Context mContext;
    private RecyclerView rv_chapter;

    public templateTreeNode templateRoot;
    //ProgressDialog progressDialog;
//    private ArrayList<Content> chapterList = new ArrayList<>();

    public fetchChapterList(String userID, int templateCode, Context mContext, RecyclerView rv_chapter) {
        this.userID = userID;
        this.templateCode = templateCode;
        this.mContext = mContext;
        this.rv_chapter = rv_chapter;
        Content template = new Content(templateCode, null, null, null, 0 );
        templateRoot = new templateTreeNode(template);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(mContext);
//        progressDialog.setMessage("데이터를 가져오는 중...");
//        progressDialog.setCancelable(true);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
//        progressDialog.show();
    }

    @Override
    protected templateTreeNode doInBackground(String... strings) {
        String url = strings[0];


        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .add("templateCode", String.valueOf(templateCode))
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response responses = null;

        try {
            responses = okHttpClient.newCall(request).execute();
            Log.d("fetchChapter", "res");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchChapter", "IOE");
            return null;
        }

        String jsonData = null;
        try {
            jsonData = responses.body().string();
            JSONArray chapterArray = new JSONArray(jsonData);

            for(int i = 0; i < chapterArray.length(); i++)
            {
                JSONObject chapterObject = chapterArray.getJSONObject(i);
                int chapCode = chapterObject.getInt("chapterCode");
                String chapName =chapterObject.getString("chapterName");
                int status = chapterObject.getInt("status");
                Content content = new Content(chapCode, chapName,null, null ,status);
                templateTreeNode Achapter = new templateTreeNode(content);
                templateRoot.addChild(Achapter);

            }
            return templateRoot;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(templateTreeNode templateNode) {
        super.onPostExecute(templateNode);
        //progressDialog.dismiss();
        chapterListAdapter myChapterAdapter = new chapterListAdapter(mContext, templateNode);
        Log.d("chapAdater", "CALLED");
        myChapterAdapter.notifyDataSetChanged();
        rv_chapter.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_chapter.setAdapter(myChapterAdapter);
    }
}

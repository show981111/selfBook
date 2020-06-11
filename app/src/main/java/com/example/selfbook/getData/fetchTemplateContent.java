package com.example.selfbook.getData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.Data.userInfo;
import com.example.selfbook.MyDraftActivity;
import com.example.selfbook.recyclerView.bookCoverAdapter;
import com.example.selfbook.recyclerView.chapterListAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class fetchTemplateContent extends AsyncTask<String, Void, templateTreeNode > {

    private String userID;
    private int templateCode;
    private Context mContext;
    private RecyclerView rv_chapter;

    private templateTreeNode templateTree;

    public fetchTemplateContent(String userID, int templateCode, Context mContext, RecyclerView rv_chapter) {
        this.userID = userID;
        this.templateCode = templateCode;
        this.mContext= mContext;
        this.rv_chapter = rv_chapter;
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
            Log.d("fetchTemplate", "res");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchTemplate", "IOE");
            return null;
        }

        String jsonData = null;
        try {
            Log.d("fetchTemplate", "jsonTry");
            jsonData = responses.body().string();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONObject Job = Jobject.getJSONObject("result");// template 정보 파싱

            int tempCode = Job.getInt("templateCode");
            String tempName = Job.getString("templateName");
            userAnswer templateInfo = new userAnswer(tempCode,tempName,null,null);//ID,name,hint,answer
            templateTree = new templateTreeNode(templateInfo);// 먼저 템플릿을 넣어

            JSONArray chapterArray = Job.getJSONArray("templateChildren");// 그 템플릿의 자식인 챕터가 들어옴
            for(int j = 0; j < chapterArray.length(); j++)
            {
                //Log.d("fetchTemplate", "chapterArrayStart");
                JSONObject chapterObject = chapterArray.getJSONObject(j);
                int chapCode = chapterObject.getInt("chapterCode");
                String chapName =chapterObject.getString("chapterName");
                userAnswer chapterInfo = new userAnswer(chapCode,chapName,null,null);

                templateTreeNode chapterTree = new templateTreeNode(chapterInfo);
                JSONArray questionArray = chapterObject.getJSONArray("chapterChildren");
                Log.d("fetchTemplate", questionArray.toString());
                //Log.d("fetchTemplate", String.valueOf(questionArray.length()));
                for(int k = 0; k < questionArray.length(); k++)
                {
                    JSONObject questionObject = questionArray.getJSONObject(k);
                    //Log.d("fetchTemplate", questionObject.toString());
                    if(questionObject.has("questionCode")  ) {
                        int questionCode = questionObject.getInt("questionCode");
                        String questionName = questionObject.getString("questionName");
                        String hint = questionObject.getString("hint");
                        String answer = questionObject.getString("answer");

                        userAnswer questionInfo = new userAnswer(questionCode, questionName, hint, answer);
                        templateTreeNode questionTree = new templateTreeNode(questionInfo);

                        chapterTree.addChild(questionTree);

                        //Log.d("fetchTemplate", "questionArray" + questionCode);
                    }
                }

                templateTree.addChild(chapterTree);
            }



            return templateTree;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(templateTreeNode templateTreeNode) {
        super.onPostExecute(templateTreeNode);
        ArrayList<Integer> chapters = new ArrayList<>();
        ArrayList<Integer> questions = new ArrayList<>();
        Log.d("fetchTemplate", templateTreeNode + "das");
        if(templateTreeNode != null) {
            List<templateTreeNode> chapterLists = templateTreeNode.getChildren();
            for (int i = 0; i < chapterLists.size(); i++) {
                templateTreeNode Achapter = chapterLists.get(i);
                userAnswer data = Achapter.getData();
                chapters.add(data.getID());
                Log.d("fetchTemplate", data.getID() + "//");
                List<templateTreeNode> questionsLists = Achapter.getChildren();
                for (int j = 0; j < questionsLists.size(); j++) {
                    templateTreeNode Aquestion = questionsLists.get(j);
                    userAnswer qData = Aquestion.getData();
                    Log.d("fetchTemplate", qData.getID() + "//");
                }
                Log.d("fetchTemplate", "------------------------");
            }
        }
        //Log.d("fetchTemplate", templateTreeNode.ge);
        chapterListAdapter myChapterAdapter = new chapterListAdapter(mContext, templateTreeNode);
        rv_chapter.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_chapter.setAdapter(myChapterAdapter);
    }
}

package com.example.selfbook.getData;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateTreeNode;
import com.example.selfbook.Data.Content;
import com.example.selfbook.recyclerView.chapterListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        if(TextUtils.isEmpty(userID) || templateCode == 0){
            return null;
        }
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
            Content templateInfo = new Content(tempCode,tempName,null,null, 0);//ID,name,hint,answer
            templateTree = new templateTreeNode(templateInfo);// 먼저 템플릿을 넣어

            JSONArray chapterArray = Job.getJSONArray("templateChildren");// 그 템플릿의 자식인 챕터가 들어옴
            int isTemplateFull = 1;
            for(int j = 0; j < chapterArray.length(); j++)
            {
                //Log.d("fetchTemplate", "chapterArrayStart");
                JSONObject chapterObject = chapterArray.getJSONObject(j);
                int chapCode = chapterObject.getInt("chapterCode");
                String chapName =chapterObject.getString("chapterName");
                Content chapterInfo = new Content(chapCode,chapName,null,null, 0);

                templateTreeNode chapterTree = new templateTreeNode(chapterInfo);
                JSONArray delegateArray = chapterObject.getJSONArray("chapterChildren");
                //Log.d("fetchTemplate", delegateArray.toString());
                Log.d("fetchTemplate", String.valueOf(chapCode));
                int isFull = 1;
                for(int k = 0; k < delegateArray.length(); k++)//get all child Delegate
                {
                    JSONObject delegateObject = delegateArray.getJSONObject(k);

                    if(delegateObject.has("delegateCode")  ) {
                        int delegateCode = delegateObject.getInt("delegateCode");
                        String delegateName = delegateObject.getString("delegateName");
                        String delegateHint = delegateObject.getString("delegateHint");
                        String delegateAnswer = delegateObject.getString("delegateAnswer");
                        //Log.d("fetchTemplate",  String.valueOf(delegateCode));
                        if(delegateAnswer != null && !TextUtils.isEmpty(delegateAnswer) && !delegateAnswer.equals("null")){
                           // Log.d("delegateAnswer", "full");
                            isFull = isFull * 1;
                        }else{
                            isFull = 0;
                        }
                        Content delegateInfo = new Content(delegateCode, delegateName, delegateHint, delegateAnswer,0);
                        templateTreeNode delegateTree = new templateTreeNode(delegateInfo);

                        chapterTree.addChild(delegateTree);
                        JSONArray detailArray = delegateObject.getJSONArray("delegateChildren");

                        Log.d("fetchTemplateDelegate", String.valueOf(delegateCode));
                        int isDetailFull = 1;
                        for(int l = 0; l < detailArray.length(); l++)//get detail question
                        {
                            JSONObject detailObject = detailArray.getJSONObject(l);
                            //Log.d("fetchTemplateDetail", "dsa");
                            if(detailObject.has("detailCode") ) {
                                int detailCode = detailObject.getInt("detailCode");
                                String detailName = detailObject.getString("detailName");
                                String detailHint = detailObject.getString("detailHint");
                                String detailAnswer = detailObject.getString("detailAnswer");
                                Log.d("fetchTemplateDetail", String.valueOf(detailCode));
                                if(detailAnswer != null && !TextUtils.isEmpty(detailAnswer) && !detailAnswer.equals("null")){
                                    Log.d("delegateAnswer", "full");
                                    isDetailFull = isDetailFull * 1;
                                }else{
                                    isDetailFull = 0;
                                }
                                Content detailInfo = new Content(detailCode, detailName, detailHint, detailAnswer,0);
                                templateTreeNode detailTree = new templateTreeNode(detailInfo);
                                delegateTree.addChild(detailTree);
                            }
                        }
                        if(isDetailFull == 1)
                        {
                            isFull = isFull * 1;
                        }else{
                            isFull = 0;
                        }
                        //Log.d("fetchTemplate", "questionArray" + questionCode);
                    }
                }
                Log.d("chapterAnswer", "--------------------");
                if(isFull == 1 )
                {
                    chapterTree.getData().setHint("full");
                    isTemplateFull = isTemplateFull * 1;
                    //Log.d("chap", "full"+chapterTree.getData().getID());
                }else{
                    isTemplateFull = 0;
                }
                templateTree.addChild(chapterTree);
            }
            if(isTemplateFull == 1)
            {
                templateTree.getData().setHint("full");
                //Log.d("chap", "full Temp"+templateTree.getData().getID());
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
        if(templateTreeNode == null) return;
//        ArrayList<Integer> chapters = new ArrayList<>();
//        ArrayList<Integer> questions = new ArrayList<>();
//        Log.d("fetchTemplate", templateTreeNode + "das");
//        if(templateTreeNode != null) {
//            List<templateTreeNode> chapterLists = templateTreeNode.getChildren();
//            for (int i = 0; i < chapterLists.size(); i++) {
//                templateTreeNode Achapter = chapterLists.get(i);
//                userAnswer data = Achapter.getData();
//                chapters.add(data.getID());
//                Log.d("fetchTemplate", data.getID() + "//");
//                List<templateTreeNode> questionsLists = Achapter.getChildren();
//                for (int j = 0; j < questionsLists.size(); j++) {
//                    templateTreeNode Aquestion = questionsLists.get(j);
//                    userAnswer qData = Aquestion.getData();
//                    Log.d("fetchTemplate", qData.getID() + "//");
//                }
//                Log.d("fetchTemplate", "------------------------");
//            }
//        }
        //Log.d("fetchTemplate", templateTreeNode.ge);

        chapterListAdapter myChapterAdapter = new chapterListAdapter(mContext, templateTreeNode);
        Log.d("chapAdater", "CALLED");
        myChapterAdapter.notifyDataSetChanged();
        rv_chapter.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_chapter.setAdapter(myChapterAdapter);
    }
}

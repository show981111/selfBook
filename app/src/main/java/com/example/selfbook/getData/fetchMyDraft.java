package com.example.selfbook.getData;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.userInfo;
import com.example.selfbook.LoginActivity;
import com.example.selfbook.MainActivity;
import com.example.selfbook.recyclerView.bookCoverAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class fetchMyDraft extends AsyncTask<String, Void, userInfo[]> {

        private ArrayList<userInfo> userDataArrayList = new ArrayList<>();
        private String inputID;
        private String inputPW;
        private Context mContext;

        private RecyclerView rv_myDraft;
        private TextView emptyMyDraft;

        private String from;
        public fetchMyDraft(String inputID, RecyclerView rv_myDraft, TextView emptyMyDraft, Context mContext) {
            this.inputID = inputID;
            this.rv_myDraft = rv_myDraft;
            this.emptyMyDraft = emptyMyDraft;
            this.mContext = mContext;
            from = "main";
        }
        public fetchMyDraft(String inputID, String inputPW, Context mContext) {
            this.inputID = inputID;
            this.inputPW = inputPW;
            this.mContext = mContext;
            from = "login";
        }

        @Override
        protected userInfo[] doInBackground(String... strings) {
            String url = strings[0];


            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody;
            if(from.equals("login")) {
                Log.d("login","login");
                formBody = new FormBody.Builder()
                        .add("userID", inputID)
                        .add("userPassword", inputPW)
                        .build();
            }else
            {
                formBody = new FormBody.Builder()
                        .add("userID", inputID)
                        .build();
            }

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                Log.d("loginTask", "res");
                Gson gson = new Gson();
                userInfo[] userInfos = gson.fromJson(response.body().charStream(), userInfo[].class);
                Log.d("loginTask", "got");
                return userInfos;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("loginTask", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(userInfo[] client) {
            super.onPostExecute(client);
            //Log.d("login", userDataArrayList.get(0).getUserName());
//            for(userInfo userInfoItem : userDataArrayList)
//            {
//                if(userInfoItem.getUserTemplateCode() == 0)
//                {
//                    checkEmptyMyDraft = 1;
//                }else{
//                    checkEmptyMyDraft = 0;
//                    break;
//                }
//            }
//
//            if(checkEmptyMyDraft == 1)
//            {
//                rv_myDraft.setVisibility(View.GONE);
//            }else{
//                emptyMyDraft.setVisibility(View.GONE);
//                bookCoverAdapter<userInfo> myDraftAdapter = new bookCoverAdapter<userInfo>(this, userDataArrayList);
//                rv_myDraft.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//                rv_myDraft.setAdapter(myDraftAdapter);
//            }
            int checkEmptyMyDraft = 0;
            if(client != null && client.length > 0) {
                for (userInfo item : client) {
                    userDataArrayList.add(item);
                }

                if(from.equals("main")) {
                    if (userDataArrayList.size() > 0) {

                        for (userInfo userInfoItem : userDataArrayList) {
                            if (userInfoItem.getUserTemplateCode() == 0) {
                                checkEmptyMyDraft = 1;
                            } else {
                                checkEmptyMyDraft = 0;
                                break;
                            }
                        }

                        if (checkEmptyMyDraft == 1) {
                            rv_myDraft.setVisibility(View.GONE);
                        } else {
                            emptyMyDraft.setVisibility(View.GONE);
                            bookCoverAdapter<userInfo> myDraftAdapter = new bookCoverAdapter<userInfo>(mContext, userDataArrayList);
                            rv_myDraft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            rv_myDraft.setAdapter(myDraftAdapter);
                        }
                    }
                }
                if(from.equals("login"))
                {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putParcelableArrayListExtra("userDataArrayList",userDataArrayList);
                    mContext.startActivity(intent);
                }

            }else{
                if(from.equals("login"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("아이디 비밀번호를 다시 확인해주세요!")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                }
            }
        }
}

package com.example.selfbook.getData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
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
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
        protected userInfo[] doInBackground(String... strings) {
            String url = strings[0];

            if(TextUtils.isEmpty(inputID)){
                return null;
            }
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody;
            if(from.equals("login")) {
                if(TextUtils.isEmpty(inputPW)) return null;

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
                Gson gson = new Gson();
                userInfo[] userInfos = gson.fromJson(response.body().charStream(), userInfo[].class);

                return userInfos;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("fetchMyDraft", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(userInfo[] client) {
            super.onPostExecute(client);
            if(client == null){
                return;
            }
            int checkEmptyMyDraft = 0;
            if(client.length > 0) {
                for (userInfo item : client) {
                    userDataArrayList.add(item);
                }

                if (userDataArrayList.size() > 0) {

                    for (userInfo userInfoItem : userDataArrayList) {
                        if (userInfoItem.getUserTemplateCode() == 0) {
                            Log.d("fetchMyDraft", "empty" +  String.valueOf(userInfoItem.getUserTemplateCode()));
                            checkEmptyMyDraft = 1;
                        } else {
                            checkEmptyMyDraft = 0;
                            break;
                        }
                    }
                    if (from.equals("main")) {
                        if (checkEmptyMyDraft == 1) {
                            Log.d("fetchMyDraft", "empty" +  String.valueOf(checkEmptyMyDraft));
                            rv_myDraft.setVisibility(View.GONE);
                            emptyMyDraft.setVisibility(View.VISIBLE);
                            emptyMyDraft.setText("구매한 원고가 없습니다!");
                        } else {
                            rv_myDraft.setVisibility(View.VISIBLE);
                            emptyMyDraft.setVisibility(View.GONE);
                            bookCoverAdapter<userInfo> myDraftAdapter = new bookCoverAdapter<userInfo>(mContext, userDataArrayList);
                            rv_myDraft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            rv_myDraft.setAdapter(myDraftAdapter);
                        }
                    }else if (from.equals("login")) {

                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putParcelableArrayListExtra("userDataArrayList", userDataArrayList);
                        mContext.startActivity(intent);
                    }
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

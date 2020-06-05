package com.example.selfbook.getData;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.selfbook.Data.userInfo;
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

        private String from;
        public fetchMyDraft(String inputID) {
            this.inputID = inputID;
            from = "main";
        }
        public fetchMyDraft(String inputID, String inputPW) {
            this.inputID = inputID;
            this.inputPW = inputPW;
            from = "login";
        }

        @Override
        protected userInfo[] doInBackground(String... strings) {
            String url = strings[0];


            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody;
            if(from.equals("login")) {
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

            if(client != null && client.length > 0) {
                for (userInfo item : client) {
                    userDataArrayList.add(item);
                }
                if (userDataArrayList.size() > 0) {
                    Log.d("login", userDataArrayList.get(0).getUserName());
                    Log.d("login", userDataArrayList.get(0).getUserID());
                    Log.d("login", String.valueOf(userDataArrayList.get(0).getUserTemplateCode()));

                }
            }
        }
}

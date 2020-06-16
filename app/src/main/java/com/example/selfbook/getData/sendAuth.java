package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class sendAuth extends AsyncTask<String, Void, String> {

    private Context context;

    private String userID;
    private String verificationCode;

    public sendAuth(Context context, String userID) {
        this.context = context;
        this.userID = userID;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID)
                    .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try {
            Log.d("sendAuth", "go");
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("sendAuth", s);
        if(s.equals("success"))
        {
            verificationCode = s;
        }else if(s.equals("none")){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("아이디가 존재하지 않습니다! 회원가입을 해주세요!")
                    .setNegativeButton("확인", null)
                    .create()
                    .show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("실패하였습니다!")
                    .setNegativeButton("확인", null)
                    .create()
                    .show();
        }
    }
}

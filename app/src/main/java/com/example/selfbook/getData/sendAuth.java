package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
    private EditText et_authCode;
    private Button bt_checkAuth;

    public sendAuth(Context context, String userID, EditText et_authCode, Button bt_checkAuth) {
        this.context = context;
        this.userID = userID;
        this.et_authCode = et_authCode;
        this.bt_checkAuth = bt_checkAuth;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        if(TextUtils.isEmpty(userID)){
            return null;
        }
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
        if(s == null) return;
        Log.d("sendAuth", s);

        if(s.length() > 7 && s.substring(0,7).equals("success"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("전송되었습니다! 이메일을 확인해주세요!(이메일이 안왔다면 스팸함을 확인해주세요!)")
                    .setNegativeButton("확인", null)
                    .create()
                    .show();
            verificationCode = s.substring(7);
            et_authCode.setVisibility(View.VISIBLE);
            bt_checkAuth.setVisibility(View.VISIBLE);
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

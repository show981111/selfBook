package com.example.selfbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.selfbook.Data.userInfo;
import com.example.selfbook.api.Api;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText register_userName = findViewById(R.id.et_registerName);
        final EditText register_userEmail = findViewById(R.id.et_registerEmail);
        final EditText register_userPassword = findViewById(R.id.et_registerPassword);
        final EditText register_userPasswordCheck = findViewById(R.id.et_registerPasswordCheck);
        Button registerSendButton = findViewById(R.id.bt_registerSend);

        registerSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register_userPassword.getText().toString().equals(register_userPasswordCheck.getText().toString()))
                {
                    registerTask registerTask = new registerTask(register_userName.getText().toString(),register_userEmail.getText()
                            .toString(), register_userPassword.getText().toString());
                    registerTask.execute(Api.POST_REGISTER);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("비밀번호와 비밀번호 확인이 일치하지 않습니다!")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }
            }
        });
    }

    class registerTask extends AsyncTask<String, Void, String> {

        private String userName;
        private String userID;
        private String userPassword;

        public registerTask(String userName, String userID, String userPassword) {
            this.userName = userName;
            this.userID = userID;
            this.userPassword = userPassword;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];

            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID )
                    .add("userPassword", userPassword)
                    .add("userName",userName)
                    .build();
            Log.d("Register", userID);
            Log.d("Register", userName);
            Log.d("Register", userPassword);

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Register", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Register", s);
            if(s.equals("success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("회원가입에 성공하였습니다! 로그인해주세요!")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("회원가입에 실패하였습니다")
                        .setNegativeButton("확인", null)
                        .create()
                        .show();
            }
        }
    }

}

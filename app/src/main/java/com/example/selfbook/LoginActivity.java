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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userID = findViewById(R.id.et_loginEmail);
        final EditText userPassword = findViewById(R.id.et_loginPassword);
        Button loginButton = findViewById(R.id.bt_login);
        Button login_registerButton = findViewById(R.id.bt_loginRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTask loginTask = new loginTask(userID.getText().toString(),userPassword.getText().toString());
                loginTask.execute(Api.GET_USERINFO);
            }
        });

        login_registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    class loginTask extends AsyncTask<String, Void, userInfo[]> {

        private ArrayList<userInfo> userDataArrayList = new ArrayList<>();
        private String inputID;
        private String inputPW;

        public loginTask(String inputID, String inputPW) {
            this.inputID = inputID;
            this.inputPW = inputPW;
        }

        @Override
        protected userInfo[] doInBackground(String... strings) {
            String url = strings[0];


            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("userID", inputID )
                    .add("userPassword", inputPW)
                    .build();

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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("userDataArrayList",userDataArrayList);
                    LoginActivity.this.startActivity(intent);
                }
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("아이디와 비밀번호를 다시한번 확인해주세요!")
                        .setNegativeButton("다시 시도", null)
                        .create()
                        .show();
            }
        }
    }
}

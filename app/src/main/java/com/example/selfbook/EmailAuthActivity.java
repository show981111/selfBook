package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.selfbook.api.Api;
import com.example.selfbook.getData.sendAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmailAuthActivity extends AppCompatActivity {

    private sendAuth sendAuth;
    private EditText et_email;
    private EditText et_authCode;
    private EditText et_resetPassword;
    private EditText et_resetPasswordCheck;
    private Button bt_resetPasswordRequest;
    private Button bt_checkAuth;
    private Button bt_sendAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);

        et_email = findViewById(R.id.et_enterEmail);
        et_authCode = findViewById(R.id.et_enterAuthCode);
        et_resetPassword = findViewById(R.id.et_registerPassword);
        et_resetPasswordCheck = findViewById(R.id.et_registerPasswordCheck);
        bt_resetPasswordRequest = findViewById(R.id.bt_resetPasswordRequest);

        bt_sendAuth = findViewById(R.id.bt_sendAuth);
        bt_checkAuth = findViewById(R.id.bt_checkAuth);

        et_authCode.setVisibility(View.GONE);
        et_resetPassword.setVisibility(View.GONE);
        et_resetPasswordCheck.setVisibility(View.GONE);
        bt_checkAuth.setVisibility(View.GONE);

        bt_sendAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches()) {

                    sendAuth = new sendAuth(EmailAuthActivity.this,et_email.getText().toString(),et_authCode, bt_checkAuth);
                    sendAuth.execute(Api.SEND_AUTH);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EmailAuthActivity.this);
                    builder.setMessage("이메일 형식이 올바르지 않습니다!")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                }
            }
        });

        bt_checkAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_authCode.getText().toString().equals(sendAuth.getVerificationCode())){
                    et_resetPassword.setVisibility(View.VISIBLE);
                    et_resetPasswordCheck.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(EmailAuthActivity.this,"인증코드가 일치하지 않습니다!",Toast.LENGTH_LONG).show();
                }
            }
        });

        bt_resetPasswordRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_resetPassword.getText().toString().equals(et_resetPasswordCheck.getText().toString()))
                {
                    resetPW resetPW = new resetPW(et_email.getText().toString(), et_resetPassword.getText().toString());
                    resetPW.execute(Api.POST_RESETPW);
                }
            }
        });

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId())
//                {
//                    case R.id.home :
//                        Intent intent = new Intent(EmailAuthActivity.this, MainActivity.class);
//                        EmailAuthActivity.this.startActivity(intent);
//                        break;
//                    case R.id.login :
//                        Intent intent1 = new Intent(EmailAuthActivity.this, LoginActivity.class);
//                        EmailAuthActivity.this.startActivity(intent1);
//                        break;
//
//                }
//                return false;
//            }
//        });

    }

    class resetPW extends AsyncTask<String, Void, String> {

        private String userID;
        private String userPassword;

        public resetPW(String userID, String userPassword) {
            this.userID = userID;
            this.userPassword = userPassword;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID)
                    .add("userPassword", userPassword)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Log.d("resetPW", "go");
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
            if(s.equals("success"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(EmailAuthActivity.this);
                builder.setMessage("성공적으로 비밀번호를 설정했습니다! 로그인해주세요!")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EmailAuthActivity.this, LoginActivity.class);
                                EmailAuthActivity.this.startActivity(intent);
                            }
                        })
                        .create()
                        .show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(EmailAuthActivity.this);
                builder.setMessage("실패하였습니다. 다시 시도해주세요 ㅠㅠ")
                       .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        }
    }

}

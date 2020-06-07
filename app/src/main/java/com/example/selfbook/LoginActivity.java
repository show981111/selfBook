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
import com.example.selfbook.getData.fetchMyDraft;
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
                fetchMyDraft fetchMyDraft = new fetchMyDraft(userID.getText().toString(),userPassword.getText().toString(),LoginActivity.this);
                fetchMyDraft.execute(Api.GET_USERINFO);
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

}

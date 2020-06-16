package com.example.selfbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.selfbook.api.Api;
import com.example.selfbook.getData.sendAuth;

public class EmailAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);

        final EditText et_email = findViewById(R.id.et_enterEmail);
        EditText et_authCode = findViewById(R.id.et_enterAuthCode);
        EditText et_resetPassword = findViewById(R.id.et_registerPassword);
        EditText et_resetPasswordCheck = findViewById(R.id.et_registerPasswordCheck);

        Button bt_sendAuth = findViewById(R.id.bt_sendAuth);
        Button bt_checkAuth = findViewById(R.id.bt_checkAuth);

        et_authCode.setVisibility(View.GONE);
        et_resetPassword.setVisibility(View.GONE);
        et_resetPasswordCheck.setVisibility(View.GONE);
        bt_checkAuth.setVisibility(View.GONE);

        bt_sendAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches()) {

                    sendAuth sendAuth = new sendAuth(EmailAuthActivity.this,et_email.getText().toString());
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
    }
}

package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchOverView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.selfbook.MainActivity.userID;

public class OverViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        getSupportActionBar().setTitle("미리보기");
        int templateCode;
        TextView tv_overView = findViewById(R.id.tv_overView);
        Intent intent =getIntent();
        templateCode = intent.getIntExtra("templateCode", -1);
        if(templateCode != -1) {
            fetchOverView fetchOverView = new fetchOverView(this, userID, templateCode, tv_overView);
            fetchOverView.execute(Api.GET_OVERVIEW);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_makeDraft);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(OverViewActivity.this, MainActivity.class);
                        OverViewActivity.this.startActivity(intent);
                        break;
                    case R.id.makedoc://원고 생성
                        AlertDialog.Builder builder = new AlertDialog.Builder(OverViewActivity.this);
                        builder.setMessage("파일을 다운로드 하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                        Uri uri = Uri.parse(Api.BASE_URL + "/document/testTemplateVersion.docx");
                                        DownloadManager.Request request = new DownloadManager.Request(uri);
                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        Long reference = downloadManager.enqueue(request);
                                    }
                                })
                                .setNegativeButton("취소",null)
                                .create()
                                .show();
                        break;

                }
                return false;
            }
        });
    }
}

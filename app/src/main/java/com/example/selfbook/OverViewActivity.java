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
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchOverView;
import com.example.selfbook.getData.makeDocx;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import static com.example.selfbook.MainActivity.userID;

public class OverViewActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        getSupportActionBar().setTitle("미리보기");

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        final int templateCode;
        TextView tv_overView = findViewById(R.id.tv_overView);
        Intent intent =getIntent();
        templateCode = intent.getIntExtra("templateCode", -1);
        if(templateCode != -1) {
            fetchOverView fetchOverView = new fetchOverView(this, userID, templateCode, tv_overView, progressBar);
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
                        makeDocx makeDocx = new makeDocx(userID, templateCode, OverViewActivity.this);
                        makeDocx.execute(Api.POST_MAKEDOCX);

                        break;

                }
                return false;
            }
        });
    }
}

package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                        break;

                }
                return false;
            }
        });
    }
}

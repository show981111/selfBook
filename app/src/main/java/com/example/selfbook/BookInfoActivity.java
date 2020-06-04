package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.selfbook.Data.templateInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Intent intent = getIntent();
        templateInfo templateInfoItem = intent.getParcelableExtra("templateInfo");
        Log.d("BookInfoActivity", String.valueOf(templateInfoItem.getTemplateCode()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(BookInfoActivity.this, MainActivity.class);
                        BookInfoActivity.this.startActivity(intent);
                        //돌아왓을때 나의 원고 리사이클러뷰 로딩해줄 필요가 있다
                        break;
                }
                return false;
            }
        });

    }
}

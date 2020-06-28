package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.selfbook.Data.Content;
import com.example.selfbook.getData.fetchDetailList;
import com.example.selfbook.recyclerView.delegateListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecyclerView rv_detail;
    int templateCode;
    ArrayList<Content> detailList = new ArrayList<>();
    delegateListAdapter detailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rv_detail = findViewById(R.id.rv_detail);
        Intent intent = getIntent();
        templateCode = intent.getIntExtra("templateCode", -1);
        detailList = intent.getParcelableArrayListExtra("detailList");

        detailListAdapter = new delegateListAdapter(this, detailList);
        rv_detail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_detail.setAdapter(detailListAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_myDraftNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        DetailActivity.this.startActivity(intent);
                        break;
                    case R.id.overview:
                        if(templateCode != -1) {
                            Intent intentOverView = new Intent(DetailActivity.this, OverViewActivity.class);
                            intentOverView.putExtra("templateCode", templateCode);
                            DetailActivity.this.startActivity(intentOverView);
                        }
                        break;
                }
                return false;
            }
        });
    }
}

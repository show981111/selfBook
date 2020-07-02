package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.selfbook.Data.userInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MyDraftActivity extends AppCompatActivity {

    private userInfo userPurchaseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_draft);

        Intent intent = getIntent();
        userPurchaseInfo = intent.getParcelableExtra("userPurchaseInfo");
        Fragment[] arrFragments = new Fragment[2];
        getSupportActionBar().setTitle("내 원고");
        arrFragments[0] = BasicDraftInfoFragment.newInstance(userPurchaseInfo);
        arrFragments[1] = ChapterDraftFragment.newInstance(userPurchaseInfo);

        TabLayout tl_myDraft = findViewById(R.id.tl_myDraft);

        ViewPager viewPager = findViewById(R.id.vp_pager_myDraft);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,arrFragments,"myDraft");
        viewPager.setAdapter(viewPagerAdapter);

        tl_myDraft.setupWithViewPager(viewPager);

//        RecyclerView rv_chapterList = findViewById(R.id.rv_cahpter);
//        fetchTemplateContent fetchTemplateContent = new fetchTemplateContent(userPurchaseInfo.getUserID() ,
//                userPurchaseInfo.getUserTemplateCode(), this , rv_chapterList );
//        fetchTemplateContent.execute(Api.GET_getTemplateContent);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_myDraftNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(MyDraftActivity.this, MainActivity.class);
                        MyDraftActivity.this.startActivity(intent);
                        break;
                    case R.id.overview:
                        Intent intentOverView = new Intent(MyDraftActivity.this, OverViewActivity.class);
                        intentOverView.putExtra("templateCode", userPurchaseInfo.getUserTemplateCode());
                        MyDraftActivity.this.startActivity(intentOverView);
                        break;
                }
                return false;
            }
        });

    }

}

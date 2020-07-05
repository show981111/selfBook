package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.Data.userInfo;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchGuideBook;
import com.example.selfbook.getData.fetchMyDraft;
import com.example.selfbook.recyclerView.bookCoverAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String userID = "";
    public static String userName = "";
    BottomNavigationView bottomNavigationView;
    RecyclerView rv_myDraft;
    TextView emptyMyDraft;
    int onCreateCalled = 0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActionBar bar = getActionBar();
        //getSupportActionBar().setTitle("나만의 자서전 만들기:)");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        RecyclerView rv_guideBook = findViewById(R.id.rv_guideBook);
        fetchGuideBook fetchGuideBook = new fetchGuideBook(this,rv_guideBook,progressBar);
        fetchGuideBook.execute(Api.GET_TEMPLATEINFO);
        ArrayList<userInfo> userDataArrayList = new ArrayList<>();
        rv_myDraft = findViewById(R.id.rv_myDraft);
        emptyMyDraft = findViewById(R.id.tv_emptyMyDraft);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        onCreateCalled = 1;
        //Log.d("fetchMyDraftOn",String.valueOf(onCreateCalled));
        if (userID != null && !TextUtils.isEmpty(userID) && userName != null && !TextUtils.isEmpty(userName)) {
            //Log.d("fetchMyDraft", "Oncalled");
            bottomNavigationView.getMenu().findItem(R.id.login).setTitle(userName);
            fetchMyDraft fetchMyDraft = new fetchMyDraft(userID, rv_myDraft, emptyMyDraft, this);
            fetchMyDraft.execute(Api.GET_USERINFO);
        } else {
            emptyMyDraft.setText("로그인을 해주세요!");
        }

        //login Selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.login :
                        if(userID == null || userID.equals("")) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;
                        }
                }
                return false;
            }
        });

        if( getIntent().getExtras() != null)
        {
            Intent intent = getIntent();
            userDataArrayList = intent.getParcelableArrayListExtra("userDataArrayList");
            if(userDataArrayList != null && userDataArrayList.size() > 0)
            {
                userID = userDataArrayList.get(0).getUserID();
                userName = userDataArrayList.get(0).getUserName();
                //Log.d("Main",userID);
                int checkEmptyMyDraft = 0;
                for(userInfo userInfoItem : userDataArrayList)
                {
                    if(userInfoItem.getUserTemplateCode() == 0)
                    {
                        emptyMyDraft.setText("구매한 원고가 없습니다!");
                        checkEmptyMyDraft = 1;
                    }else{
                        checkEmptyMyDraft = 0;
                        break;
                    }
                }

                if(checkEmptyMyDraft == 1)
                {
                    rv_myDraft.setVisibility(View.GONE);
                }else{
                    emptyMyDraft.setVisibility(View.GONE);
                    bookCoverAdapter<userInfo> myDraftAdapter = new bookCoverAdapter<userInfo>(this, userDataArrayList);
                    rv_myDraft.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
                    rv_myDraft.setAdapter(myDraftAdapter);
                }

                bottomNavigationView.getMenu().findItem(R.id.login).setTitle(userDataArrayList.get(0).getUserName());
            }else{
                Toast.makeText(MainActivity.this,"다시한번 시도해주세요!",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        onCreateCalled = 0;
//        Log.d("fetchMyDraftPause",String.valueOf(onCreateCalled));
//        Log.d("fetchMyDraft", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("fetchMyDraftResume",String.valueOf(onCreateCalled));
        if(rv_myDraft != null && emptyMyDraft != null && onCreateCalled == 0) {
            if (userID != null && !TextUtils.isEmpty(userID) && userName != null && !TextUtils.isEmpty(userName)) {
                bottomNavigationView.getMenu().findItem(R.id.login).setTitle(userName);
                fetchMyDraft fetchMyDraft = new fetchMyDraft(userID, rv_myDraft, emptyMyDraft, this);
                fetchMyDraft.execute(Api.GET_USERINFO);
            } else {
                emptyMyDraft.setText("로그인을 해주세요!");
            }
            //Log.d("fetchMyDraft", "recalled");
        }
    }
}

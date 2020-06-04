package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.Data.userInfo;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchGuideBook;
import com.example.selfbook.recyclerView.bookCoverAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_guideBook = findViewById(R.id.rv_guideBook);
        fetchGuideBook fetchGuideBook = new fetchGuideBook(this,rv_guideBook);
        fetchGuideBook.execute(Api.GET_TEMPLATEINFO);
        ArrayList<userInfo> userDataArrayList = new ArrayList<>();
        RecyclerView rv_myDraft = findViewById(R.id.rv_myDraft);
        TextView emptyMyDraft = findViewById(R.id.tv_emptyMyDraft);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //login Selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.login :
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        MainActivity.this.startActivity(intent);
                        //돌아왓을때 나의 원고 리사이클러뷰 로딩해줄 필요가 있다
                        break;
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
                int checkEmptyMyDraft = 0;
                for(userInfo userInfoItem : userDataArrayList)
                {
                    if(userInfoItem.getUserTemplateCode() == 0)
                    {
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
}

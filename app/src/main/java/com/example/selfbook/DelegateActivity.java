package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.selfbook.Data.Content;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.fetchDetailList;
import com.example.selfbook.getData.postUserAnswer;
import com.example.selfbook.getData.skipDelegateAndDetail;
import com.example.selfbook.helper.MyButtonClickListener;
import com.example.selfbook.helper.mySwipeHelper;
import com.example.selfbook.recyclerView.delegateListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.selfbook.MainActivity.userID;

public class DelegateActivity extends AppCompatActivity {

    private ArrayList<Content> delegateArray = new ArrayList<>();
    private delegateListAdapter delegateListAdapter;
    private String chapterName;
    private int chapterNum;
    private String title = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        final int templateCode;
        final RecyclerView rv_questionList = findViewById(R.id.rv_question);
        Intent intent = getIntent();
        templateCode = intent.getIntExtra("templateCode", -1);
        chapterNum = intent.getIntExtra("chapterNum",-1);
        chapterName = intent.getStringExtra("chapterName");
        delegateArray = intent.getParcelableArrayListExtra("delegateArray");

        if(chapterNum != -1 && !TextUtils.isEmpty(chapterName)){
            title = chapterNum + ". " + chapterName;
            getSupportActionBar().setTitle(title);
        }
        //getSupportActionBar().setTitle(chapnum + ". " + chapterList.get(position).getData().getName());

//        guideBookAdapter = new bookCoverAdapter<templateInfo>(context, templateInfoArrayList);
//        rv_guideBook.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
//        rv_guideBook.setAdapter(guideBookAdapter);

        delegateListAdapter = new delegateListAdapter(this, delegateArray);
        rv_questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_questionList.setAdapter(delegateListAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_myDraftNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(DelegateActivity.this, MainActivity.class);
                        DelegateActivity.this.startActivity(intent);
                        break;
                    case R.id.overview:
                        if(templateCode != -1) {
                            Intent intentOverView = new Intent(DelegateActivity.this, OverViewActivity.class);
                            intentOverView.putExtra("templateCode", templateCode);
                            DelegateActivity.this.startActivity(intentOverView);
                        }
                        break;
                }
                return false;
            }
        });

        final mySwipeHelper swipeHelper = new mySwipeHelper(this, rv_questionList, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<mySwipeHelper.MyButton> buffer) {
                Log.d("ini", "ini");
                buffer.add(new MyButton(getApplicationContext(), "skip", 60, 0, Color.parseColor("#266ee0"), new MyButtonClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                    @Override
                    public void onClick(int pos) {
                        Log.d("skip", "clickled");
                        //자식을 모두 캔슬시켜야 함 여기서 //
                        //Toast.makeText(getApplicationContext(), "delete Click"+pos, Toast.LENGTH_SHORT).show();
                        if(rv_questionList.findViewHolderForAdapterPosition(pos) != null) {
                            Log.d("skip", "clickled"+ pos);
                            //View itemView = rv_questionList.findViewHolderForAdapterPosition(pos).itemView;
                            //EditText et_typeAnswer = itemView.findViewById(R.id.et_typeAnswer);
                            skipDelegateAndDetail skipDelegateAndDetail = new skipDelegateAndDetail(getApplicationContext(), delegateArray.get(pos).getID(),
                                                                                                    userID,delegateArray, pos, delegateListAdapter);
                            skipDelegateAndDetail.execute(Api.POST_SKIPDELEGATE);

                        }

                    }
                }));
                buffer.add(new MyButton(getApplicationContext(), "detail", 60, 0, Color.parseColor("#FF9502"), new MyButtonClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                    @Override
                    public void onClick(int pos) {
                        //String userID, int delegateCode, Context mContext, RecyclerView rv_detail, templateTreeNode delegateNode
                        fetchDetailList fetchDetailList = new fetchDetailList(userID, delegateArray.get(pos).getID(),getApplicationContext(), templateCode, pos, title);
                        fetchDetailList.execute(Api.GET_DETAILLIST);
                        Log.d("skip", "clickledDetail");

                    }
                }));

            }
        };


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Log.d("chapAdap","backPress");

    }
}

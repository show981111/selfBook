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

public class DetailActivity extends AppCompatActivity {

    RecyclerView rv_detail;
    int templateCode;
    ArrayList<Content> detailList = new ArrayList<>();
    delegateListAdapter detailListAdapter;
    int pos;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rv_detail = findViewById(R.id.rv_detail);
        Intent intent = getIntent();
        templateCode = intent.getIntExtra("templateCode", -1);
        title = intent.getStringExtra("title");
        pos = intent.getIntExtra("pos", -1);
        if(pos != -1) {
            pos++;
            title = title + " - " + pos + "";
            getSupportActionBar().setTitle(title);
        }
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

        final mySwipeHelper swipeHelper = new mySwipeHelper(this, rv_detail, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                Log.d("ini", "ini");
                buffer.add(new MyButton(getApplicationContext(), "skip", 60, 0, Color.parseColor("#266ee0"), new MyButtonClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                    @Override
                    public void onClick(int pos) {
                        Log.d("skip", "clickled");
                        //detail만 캔 //
                        //Toast.makeText(getApplicationContext(), "delete Click"+pos, Toast.LENGTH_SHORT).show();
                        if(rv_detail.findViewHolderForAdapterPosition(pos) != null) {
                            Log.d("skip", "clickled");
                            View itemView = rv_detail.findViewHolderForAdapterPosition(pos).itemView;
                            //EditText et_typeAnswer = itemView.findViewById(R.id.et_typeAnswer);
                            //Context context, int key, String input, String userID, ArrayList<Content> detailList,
                            //                          String from, delegateListAdapter delegateListAdapter, int pos)
                            postUserAnswer postUserAnswer = new postUserAnswer(getApplicationContext(), detailList.get(pos).getID(),"skipped",userID,detailList,"detail",
                                                                                detailListAdapter,pos);
                            postUserAnswer.execute(Api.POST_SETUSERANSWER);
                            itemView.findViewById(R.id.ll_questionItem).setBackgroundColor(Color.rgb(190, 185, 201));
                            //et_typeAnswer.setText("skipped");
                        }
                    }
                }));
            }
        };
    }
}

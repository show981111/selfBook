package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.selfbook.Data.userAnswer;
import com.example.selfbook.api.Api;
import com.example.selfbook.getData.postUserAnswer;
import com.example.selfbook.helper.MyButtonClickListener;
import com.example.selfbook.helper.mySwipeHelper;
import com.example.selfbook.recyclerView.questionListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.selfbook.MainActivity.userID;

public class QuestionActivity extends AppCompatActivity {

    private ArrayList<userAnswer> questionArray = new ArrayList<>();
    private questionListAdapter questionListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final RecyclerView rv_questionList = findViewById(R.id.rv_question);
        Intent intent = getIntent();
        questionArray = intent.getParcelableArrayListExtra("questionArray");

//        guideBookAdapter = new bookCoverAdapter<templateInfo>(context, templateInfoArrayList);
//        rv_guideBook.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
//        rv_guideBook.setAdapter(guideBookAdapter);

        questionListAdapter = new questionListAdapter(this, questionArray);
        rv_questionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_questionList.setAdapter(questionListAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_myDraftNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                        QuestionActivity.this.startActivity(intent);
                        break;
                }
                return false;
            }
        });

        final mySwipeHelper swipeHelper = new mySwipeHelper(this, rv_questionList, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(getApplicationContext(), "skip", 60, 0, Color.parseColor("#266ee0"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        //Toast.makeText(getApplicationContext(), "delete Click"+pos, Toast.LENGTH_SHORT).show();
                        if(rv_questionList.findViewHolderForAdapterPosition(pos) != null) {
                            View itemView = rv_questionList.findViewHolderForAdapterPosition(pos).itemView;
                            EditText et_typeAnswer = itemView.findViewById(R.id.et_typeAnswer);
                            postUserAnswer postUserAnswer = new postUserAnswer(getApplicationContext(), questionArray.get(pos).getID(), "skipped"
                                    , userID, et_typeAnswer, "question");
                            postUserAnswer.execute(Api.POST_SETUSERANSWER);
                            itemView.findViewById(R.id.ll_questionItem).setBackgroundColor(Color.rgb(190, 185, 201));
                            et_typeAnswer.setText("skipped");
                        }
                        questionListAdapter.notifyDataSetChanged();
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

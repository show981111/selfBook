package com.example.selfbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.api.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.selfbook.MainActivity.userID;
import static com.example.selfbook.MainActivity.userName;

public class BookInfoActivity extends AppCompatActivity {

    private templateInfo templateInfoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Intent intent = getIntent();
        templateInfoItem = intent.getParcelableExtra("templateInfo");
        Log.d("BookInfoActivity", String.valueOf(templateInfoItem.getTemplateCode()));
        Log.d("BookInfoActivity", userID+"USER");

        TextView tv_bookPrice = findViewById(R.id.tv_guideBookPrice);
        TextView tv_templateTitle = findViewById(R.id.tv_templateTitle);
        TextView tv_authorName = findViewById(R.id.tv_authorName);
        TextView tv_madeDate = findViewById(R.id.tv_madeDate);


        Fragment[] arrFragments = new Fragment[2];
        arrFragments[0] = new TemplateIntroFragment();
        arrFragments[1] = new PreviewFragment();
        //일단 책소개와 미리보기만 만듬 저자소개는 필요가 없을듯 하다 글지?
        TabLayout tl_bookIntro = findViewById(R.id.tl_bookIntro);

        ViewPager vp_pager_bookInfo = findViewById(R.id.vp_pager_bookInfo);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, arrFragments, "bookInfo");
        vp_pager_bookInfo.setAdapter(viewPagerAdapter);

        tl_bookIntro.setupWithViewPager(vp_pager_bookInfo);


        tv_bookPrice.setText(String.valueOf(templateInfoItem.getBookPrice()));
        tv_templateTitle.setText(templateInfoItem.getTemplateName());
        tv_authorName.setText(templateInfoItem.getAuthor());
        tv_madeDate.setText(templateInfoItem.getMadeDate());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bookNavi);
        if(userName != null && !userName.equals("")){
            bottomNavigationView.getMenu().findItem(R.id.login).setTitle(userName);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.bottom_navi_bookmark :
                       //북마크에 추가
                        break;

                    case R.id.home :
                        Intent intent = new Intent(BookInfoActivity.this, MainActivity.class);
                        BookInfoActivity.this.startActivity(intent);
                        //돌아왓을때 나의 원고 리사이클러뷰 로딩해줄 필요가 있다
                        break;
                    case R.id.login :
                        if(userID == null || userID.equals("")) {
                            Intent intentLogin = new Intent(BookInfoActivity.this, LoginActivity.class);
                            intentLogin.putExtra("from", "login");
                            BookInfoActivity.this.startActivity(intentLogin);
                        }
                        //돌아왓을때 나의 원고 리사이클러뷰 로딩해줄 필요가 있다
                        break;

                    case R.id.bottom_navi_purchase ://책 구매!
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookInfoActivity.this);
                        AlertDialog dialog = builder.setMessage(templateInfoItem.getTemplateName()+"을 구매하시겠습니까?\n가격 : "+templateInfoItem.getBookPrice()+"원")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(userID == null || userID.equals(""))
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(BookInfoActivity.this);
                                            builder.setMessage("로그인을 먼저 해주세요!")
                                                    .setNegativeButton("확인", null)
                                                    .create()
                                                    .show();
                                        }else{
                                            setUserPurchaseTask setUserPurchase = new setUserPurchaseTask(userID,templateInfoItem.getTemplateCode());
                                            setUserPurchase.execute(Api.POST_SETUSERPURCHASE);
                                        }
                                        //POST_SETUSERPURCHASE
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(BookInfoActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        dialog.show();
                        break;
                }
                return false;
            }
        });

    }

    class setUserPurchaseTask extends AsyncTask<String, Void, String>{

        private String userID;
        private int templateCode;

        public setUserPurchaseTask(String userID, int templateCode) {
            this.userID = userID;
            this.templateCode = templateCode;
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];

            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID )
                    .add("templateCode", String.valueOf(templateCode))
                    .build();
            Log.d("Register", userID);
            Log.d("Register", String.valueOf(templateCode));

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Register", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("bookInfoPurchase", s);
            if(s.equals("success"))
            {
                Toast.makeText(BookInfoActivity.this,"성공적으로 구매하였습니다!",Toast.LENGTH_SHORT).show();
            }else if(s.equals("already")){
                Toast.makeText(BookInfoActivity.this,"이미 구매한 책입니다!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(BookInfoActivity.this,"에러가 발생했습니다! 다시한번 시도해주세요!",Toast.LENGTH_LONG).show();

            }
        }
    }
}

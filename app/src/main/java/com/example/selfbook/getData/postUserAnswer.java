package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfbook.Data.Content;
import com.example.selfbook.recyclerView.delegateListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class postUserAnswer extends AsyncTask<String, Void, String> {

    private Context context;

    private EditText et_title;

    private int key;
    private String input;
    private String userID;
    private String from;
    private ArrayList<Content> detailList;
    private delegateListAdapter delegateListAdapter;
    private int pos;

    public postUserAnswer(Context context, int key, String input, String userID, EditText et_title, String from) {
        this.context = context;
        this.key = key;
        this.input = input;
        this.userID = userID;
        this.et_title = et_title;
        this.from = from;

    }

    public postUserAnswer(Context context, int key, String input, String userID, ArrayList<Content> detailList,
                          String from, delegateListAdapter delegateListAdapter, int pos) {
        this.context = context;
        this.key = key;
        this.input = input;
        this.userID = userID;
        this.detailList = detailList;
        this.from = from;
        this.delegateListAdapter = delegateListAdapter;
        this.pos = pos;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                    .add("key", String.valueOf(key))
                    .add("userID", userID)
                    .add("input", input)
                    .add("from", from)
                    .build();

        //Log.d("postUserAnswer", startTime);

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try {
            Log.d("postUserAnswer", "go");
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("postUserAnswer", s);
        if(s.equals("success"))
        {

            if(et_title != null) {
                et_title.setBackgroundColor(Color.rgb(190, 185, 201));

            }else if(detailList != null && delegateListAdapter != null){
                detailList.get(pos).setAnswer("skipped");
                detailList.get(pos).setStatus(1);
                delegateListAdapter.notifyItemChanged(pos);
            }

            Toast toast = Toast.makeText(context, "등록하였습니다", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); // center align

        }else if(s.equals("redundant")){
            Toast toast = Toast.makeText(context,"변경 내용이 없습니다!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); // center align
        }else{
            Toast toast = Toast.makeText(context,"실패하였습니다!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); // center align
        }
    }
}

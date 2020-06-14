package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

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

    public postUserAnswer(Context context, int key, String input, String userID, EditText et_title, String from) {
        this.context = context;
        this.key = key;
        this.input = input;
        this.userID = userID;
        this.et_title = et_title;
        this.from = from;

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
        Log.d("postUserAnswer", String.valueOf(key));
        Log.d("postUserAnswer",input);
        Log.d("postUserAnswer",userID);
        Log.d("postUserAnswer",from);
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
            if( et_title != null)
            {
                et_title.setBackgroundColor(Color.rgb(190, 185, 201));
                Toast toast = Toast.makeText(context,"등록하였습니다",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show(); // center align

            }else{
                Toast toast = Toast.makeText(context,"null?",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show(); // center align
            }
        }else if(s.equals("redundant")){
            Toast toast = Toast.makeText(context,"변경 내용이 없습니다!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); // center align
        }
    }
}

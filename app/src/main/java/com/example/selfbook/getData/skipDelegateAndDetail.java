package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
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

public class skipDelegateAndDetail extends AsyncTask<String, Void, String> {

    private Context context;
    private ArrayList<Content> delegateArray = new ArrayList<>();
    private int pos;
    private int key;
    private delegateListAdapter delegateListAdapter;
    private String userID;

    public skipDelegateAndDetail(Context context, int key, String userID, ArrayList<Content> delegateArray, int pos, delegateListAdapter delegateListAdapter) {
        this.context = context;
        this.key = key;
        this.userID = userID;
        this.delegateArray = delegateArray;
        this.pos = pos;
        this.delegateListAdapter = delegateListAdapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        if(TextUtils.isEmpty(userID) || key == 0){
            return null;
        }
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                    .add("delegateCode", String.valueOf(key))
                    .add("userID", userID)
                    .build();
        Log.d("skipDelegateAndDetail", String.valueOf(key));
        Log.d("skipDelegateAndDetail",userID);
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
        if(s == null) return;
        Log.d("skipDelegateAndDetail", s);
        if(s.equals("success"))
        {
            if( delegateArray != null)
            {
                delegateArray.get(pos).setAnswer("skipped");
                delegateArray.get(pos).setStatus(1);
                delegateListAdapter.notifyItemChanged(pos);
                Toast toast = Toast.makeText(context,"등록하였습니다",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show(); // center align
                //et_DelegateAnswer.setText("skipped");

            }else{
                Toast toast = Toast.makeText(context,"오류가 발생하였습니다...",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show(); // center align
            }
        }else{
            Toast toast = Toast.makeText(context,"실패하였습니다!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show(); // center align
        }
    }
}

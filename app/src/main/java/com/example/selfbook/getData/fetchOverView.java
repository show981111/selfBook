package com.example.selfbook.getData;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
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

public class fetchOverView extends AsyncTask<String, Void, String> {

    private Context context;

    private String userID;
    private int templateCode;
    private TextView tv_overview;

    public fetchOverView(Context context, String userID, int templateCode, TextView tv_overview) {
        this.context = context;
        this.userID = userID;
        this.templateCode = templateCode;
        this.tv_overview = tv_overview;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID)
                    .add("templateCode", String.valueOf(templateCode))
                    .build();

        Log.d("fetchOverView", userID);
        Log.d("fetchOverView", String.valueOf(templateCode));

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try {
            Log.d("fetchOverView", "go");
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
        Log.d("fetchOverView", s);
        tv_overview.setText(Html.fromHtml(s));
    }
}

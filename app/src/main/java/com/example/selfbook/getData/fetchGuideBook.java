package com.example.selfbook.getData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfbook.Data.templateInfo;
import com.example.selfbook.recyclerView.bookCoverAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fetchGuideBook extends AsyncTask<String, Void, templateInfo[]> {

    private Context context;
    private bookCoverAdapter<templateInfo> guideBookAdapter;
    private RecyclerView rv_guideBook;

    private ArrayList<templateInfo> templateInfoArrayList = new ArrayList<>();

    public fetchGuideBook(Context context, RecyclerView rv_guideBook) {
        this.context = context;
        this.rv_guideBook = rv_guideBook;
    }

    @Override
    protected templateInfo[] doInBackground(String... strings) {
        String url= strings[0];

        OkHttpClient client = new OkHttpClient();
        Log.d("fetchGuideBook", "hello");
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("fetchGuideBook", "try");
            Gson gson = new Gson();
            templateInfo[] templateInfo = gson.fromJson(response.body().charStream(), templateInfo[].class);
            return templateInfo;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fetchGuideBook", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(templateInfo[] templateInfos) {
        super.onPostExecute(templateInfos);

        templateInfoArrayList.clear();
        for(templateInfo template : templateInfos)
        {
            templateInfoArrayList.add(template);
            Log.d("fetchGuideBook", String.valueOf(template.getBookPrice()));
        }

        guideBookAdapter = new bookCoverAdapter<templateInfo>(context, templateInfoArrayList);
        rv_guideBook.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        rv_guideBook.setAdapter(guideBookAdapter);
    }
}

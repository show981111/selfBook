package com.example.selfbook.getData;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.selfbook.OverViewActivity;
import com.example.selfbook.api.Api;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class makeDocx extends AsyncTask<String, Void, String> {

    private String userID;
    private int templateCode;
    private Context context;


    public makeDocx(String userID, int templateCode, Context context) {
        this.userID = userID;
        this.templateCode = templateCode;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userID", userID)
                .add("templateCode", String.valueOf(templateCode))
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        try {
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
        if(s != null)
        {
            if(s.equals("success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("파일을 다운로드 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(Api.BASE_URL + "/downloadDocx.php");
                                //Uri uri = Uri.parse(Api.BASE_URL + "/document/testTemplate.docx");
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setMimeType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "selfBook_" + templateCode + ".docx");//set Name For This
                                Long reference = downloadManager.enqueue(request);

                                //request.setMimeType("text");
                                //request.
                                //Long reference = downloadManager.enqueue(request);

                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();
            }else{
                Toast.makeText(context,"실패하였습니다. 다시 시도해주세요!",Toast.LENGTH_LONG).show();
            }
        }
    }
}

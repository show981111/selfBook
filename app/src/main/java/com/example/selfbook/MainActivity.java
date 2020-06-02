package com.example.selfbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.selfbook.api.Api;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_guideBook = findViewById(R.id.rv_guideBook);
        fetchGuideBook fetchGuideBook = new fetchGuideBook(this,rv_guideBook);
        fetchGuideBook.execute("http://13.125.206.125/getTemplateInfo.php");

    }
}

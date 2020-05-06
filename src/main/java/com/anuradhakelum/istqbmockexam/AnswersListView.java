package com.anuradhakelum.istqbmockexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Iterator;
import java.util.Map;

public class AnswersListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(this, MockExam.answerMap);
        listView.setAdapter(myCustomAdapter);
    }
}

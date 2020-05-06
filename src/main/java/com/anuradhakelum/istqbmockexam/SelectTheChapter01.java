package com.anuradhakelum.istqbmockexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

public class SelectTheChapter01 extends AppCompatActivity {

    public int selectedChapter = 0;
    SwipeSelector swipeSelector;

    private AdView mAdView;
    public static String CA_APP_PUB ="ca-app-pub-2500224556487339~8963736325";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_the_chapter01);

        swipeSelector = (SwipeSelector) findViewById(R.id.swipeSelector01);
        swipeSelector.setItems(
                // The first argument is the value for that item, and should in most cases be unique for the
                // current SwipeSelector, just as you would assign values to radio buttons.
                // You can use the value later on to check what the selected item was.
                // The value can be any Object, here we're using ints.
                new SwipeItem(0, "01. Fundamentals of Testing", "You should be able " +
                        "to answer questions from Why testing is necessary?, What is testing?, Seven " +
                        "testing principles, Fundamental testing process and The psychology of testing, Ethics of testing."),
                new SwipeItem(1, "02. Testing Throughout the Software Life Cycle", "You should be able " +
                        "to answer questions from Software Development Models, Test Levels and Test Types, Maintenance Testing."),
                new SwipeItem(2, "03. Static Techniques", "You should be able " +
                        "to answer questions from Static Techniques and the Test Process, Review Process and Static Analysis by Tools."),
                new SwipeItem(3, "04. Test Design Techniques", "You should be able " +
                        "to answer questions from The Test Development Process, Categories of Test Design Techniques," +
                        " Specification-based or Black-box Techniques, Structure-based or White-box " +
                        "Techniques, Experience-based Techniques and Choosing Test Techniques."),
                new SwipeItem(4, "05. Test Management", "You should be able " +
                        "to answer questions from Test Organization, Test Planning and Estimation, " +
                        "Test Progress Monitoring and Control, Configuration Management and  Risk and Testing, Incident Management."),
                new SwipeItem(5, "06. Tool Support for Testing", "You should be able " +
                        "to answer questions from Types of Test Tools, Effective Use of Tools: Potential " +
                        "Benefits and Risks and Introducing a Tool into an Organization."),
                new SwipeItem(6, "From All Chapters", "You should be able " +
                        "to answer questions from chapters of Fundamentals of Testing, Testing Throughout the " +
                        "Software Life Cycle, Static Techniques, Test Design Techniques, Test Management and Tool Support for Testing.")
        );


        MobileAds.initialize(getApplicationContext(), CA_APP_PUB);
        mAdView = findViewById(R.id.adView02);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    public void startButtonClick(View view) {

        SwipeItem selectedItem = swipeSelector.getSelectedItem();

        int value = (Integer) selectedItem.value;


        if (value == 0) {
            selectedChapter = 1;
        }
        if (value == 1) {
            selectedChapter = 2;
        }
        if (value == 2) {
            selectedChapter = 3;
        }
        if (value == 3) {
            selectedChapter = 4;
        }
        if (value == 4) {
            selectedChapter = 5;
        }
        if (value == 5) {
            selectedChapter = 6;
        }
        if (value == 6) {
            selectedChapter = 0;
        }else{
            selectedChapter = 0;
        }
        Intent intent = new Intent(this, GuessActivity.class);
        intent.putExtra("chapter", selectedChapter);
        startActivity(intent);
    }
}


package com.anuradhakelum.istqbmockexam;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper databaseHelper;
    public static Boolean switchPref;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //navigaton drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Check DB exist
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (database.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DatabaseHelper.DBLOCATION+DatabaseHelper.DBNAME, null, SQLiteDatabase.OPEN_READWRITE);
            int currentDB = db.getVersion();
            Log.d("ISTQB","Current DB version: "+currentDB);
            if(currentDB!=DatabaseHelper.DBVERSION){
                this.deleteDatabase(DatabaseHelper.DBNAME);
                copyDatabase(this);

            }
            databaseHelper = new DatabaseHelper(this);
            databaseHelper.getDatabaseVersion();
            databaseHelper.getWritableDatabase();
            Log.d("ISTQB","Number if questions: "+databaseHelper.getQuestionCount());
        }else{
            databaseHelper = new DatabaseHelper(this);
            databaseHelper.getWritableDatabase();
            databaseHelper.copyDataBase();
        }
        notification();

        MobileAds.initialize(this, "ca-app-pub-2500224556487339~8963736325");
        mAdView = findViewById(R.id.adView06);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notification();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mock_exam) {

            Intent intent = new Intent(this, MockExam.class);
            startActivity(intent);
        } else if (id == R.id.selectchapter) {
            Intent intent = new Intent(this, SelectTheChapter.class);
            startActivity(intent);

        } else if (id == R.id.guessanswer) {
            Intent intent = new Intent(this, GuessActivity.class);
            startActivity(intent);

        } else if (id == R.id.guesshapters) {
            Intent intent = new Intent(this, SelectTheChapter01.class);
            startActivity(intent);

        } else if (id == R.id.setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "ISTQB Mock Exam Android App");
            share.putExtra(Intent.EXTRA_TEXT, "ISTQB Mock Exam Download : https://goo.gl/yjhdpv");

            startActivity(Intent.createChooser(share, "Share link!"));

        } else if (id == R.id.nav_send) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"anuradhakelum92@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback : ISTQB Mock Exam Android App");
            i.putExtra(Intent.EXTRA_TEXT, "Write your feedback...");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mockExambuttonClick(View view) {
        MockExam.answerMap = new HashMap<>();
        Intent intent = new Intent(this, SelectTheChapter.class);
        startActivity(intent);

    }

    public boolean copyDatabase(Context context) {

        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outPutFilePath = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outPutFilePath);

            byte[] buff = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();

            Log.d("ISTQB","Database is copied to data folder");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startExamButton(View view) {
        MockExam.answerMap = new HashMap<>();
        Intent intent = new Intent(this, MockExam.class);
        startActivity(intent);
    }

    public void guessAnswer(View view) {

        Intent intent = new Intent(this, GuessActivity.class);
        startActivity(intent);
    }

    public void notification() {
        //settings
//        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_NOTIFICATION_SWITCH, false);


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        intent.putExtra("notofication", switchPref);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 7);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

//        Log.d("MainActivity", "Set Notification status : " + switchPref);
    }


    public void guesschapter(View view) {
        Intent intent = new Intent(this, SelectTheChapter01.class);
        startActivity(intent);
    }

    private void about() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("Developed by SuperMyDroid Apps\n");
    }

}

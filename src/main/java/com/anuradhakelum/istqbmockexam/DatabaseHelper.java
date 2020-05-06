package com.anuradhakelum.istqbmockexam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by anuradhawijethunga on 10/15/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "ISTQBDB.db";
    public static final String DBLOCATION = "/data/user/0/com.anuradhakelum.istqbmockexam/databases/";
    public static final int DBVERSION = 2;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("ISTQB", "Method onCreate");

    }

    public void copyDataBase() {

        try {
            InputStream myInput = context.getAssets().open(DBNAME);
            String outFileName = DBLOCATION + DBNAME;
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("ISTQB", "onUpgrade method");
//        String myPath = DBLOCATION + DBNAME;
//        sqLiteDatabase.close();
//        this.close();
//        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//        sqLiteDatabase.delete(DBNAME, null, null);
//        copyDataBase();
//        Log.d("ISTQB", "Databsse is deleted");

    }

    public void openDatabase() throws NullPointerException {
        String myPath = DBLOCATION + DBNAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();

        super.close();

    }

    public Cursor getQuestionSet(int id) {
        QuestionSet questionSet = null;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM ISTQB_QUESTION WHERE questionID = " + id, null);
            cursor.moveToFirst();
        } catch (Exception e) {
        }

        return cursor;
    }

    public Cursor getQuestionChapterRandom(int chapter) {
        QuestionSet questionSet;
        Cursor cursor = null;
        try {
            if (chapter != 0) {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM ISTQB_QUESTION  where CHAPTER = '" + chapter + "' ORDER BY RANDOM() LIMIT 1", null);//change
            } else {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM ISTQB_QUESTION ORDER BY RANDOM() LIMIT 1", null);//change
            }
            cursor.moveToFirst();

        } catch (Exception e) {
        }
        return cursor;
    }

    public Cursor getQuestionChapterMock(int chapter) {

        //openDatabase();
        QuestionSet questionSet;
        Cursor cursor = null;
        try {
            if (chapter != 0) {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM ISTQB_QUESTION  where CHAPTER = '" + chapter + "' ORDER BY RANDOM() LIMIT 40", null);//change
            } else {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM ISTQB_QUESTION ORDER BY RANDOM() LIMIT 40", null);//change
            }
            cursor.moveToFirst();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public int getDatabaseVersion() {
        int dbVersion = 0;
        openDatabase();
        dbVersion = sqLiteDatabase.getVersion();
        Log.d("ISTQB", "Database version is " + dbVersion);
        close();

        return dbVersion;
    }

    public int getQuestionCount() {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  * FROM ISTQB_QUESTION", null);
        int count = cursor.getCount();
        cursor.close();
        close();
        return count;
    }

}

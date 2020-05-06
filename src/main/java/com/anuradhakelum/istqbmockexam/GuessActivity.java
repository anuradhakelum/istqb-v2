package com.anuradhakelum.istqbmockexam;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class GuessActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Cursor cursor;
    private QuestionSet questionSet;
    int chapterNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                chapterNumber = 0;
            } else {
                chapterNumber = extras.getInt("chapter");
            }
        }
//        else {
//            chapterNumber = (Integer) savedInstanceState.getSerializable("chapter");
//        }
        //Update 2.05

        getQuestion();
    }

    private void getQuestion() {
        databaseHelper.openDatabase();
        cursor = databaseHelper.getQuestionChapterRandom(chapterNumber);
        databaseHelper.close();
        if (cursor.getCount() == 1) {
//            Log.d("GuessActivity", "cursor count is OK " + cursor.getCount());
            setQuestions();
        } else {
//            Log.d("GuessActivity", "cursor count isn't OK " + cursor.getCount());
        }
    }

    //set question and answers
    private void setQuestions() {

        questionSet = new QuestionSet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));
        setQuestionText(questionSet.getQuestion());
        setRadioButtonText(questionSet.getAnswerOne(), questionSet.getAnswerTwo(), questionSet.getAnswerThree(), questionSet.getAnswerFour());


    }

    private void setQuestionText(String questionText) {

        TextView questionTextView = (TextView) findViewById(R.id.guessquestion);
        questionTextView.setText(questionText);
    }

    //Set radio button text
    private void setRadioButtonText(String firstRadio, String secondRadio, String thridRadio, String fourthRadio) {

        RadioButton answerOne = (RadioButton) findViewById(R.id.gRone);
        RadioButton answerTwo = (RadioButton) findViewById(R.id.gRtwo);
        RadioButton answerThree = (RadioButton) findViewById(R.id.gRthree);
        RadioButton answerFour = (RadioButton) findViewById(R.id.gRfour);

        answerOne.setText(firstRadio);
        answerTwo.setText(secondRadio);
        answerThree.setText(thridRadio);
        answerFour.setText(fourthRadio);
    }

    public void gradioOneClick(View view) {
        resetRadioButtons(1);
    }

    public void gradioTwoClick(View view) {
        resetRadioButtons(2);
    }

    public void gradioThreeClick(View view) {
        resetRadioButtons(3);
    }

    public void gradioFourClick(View view) {
        resetRadioButtons(4);
    }

    public void shuffleClick(View view) {

        resetRadioButtons(0);
        getQuestion();
    }

    //reset radio buttons
    private void resetRadioButtons(int id) {

        RadioButton answerOne = (RadioButton) findViewById(R.id.gRone);
        RadioButton answerTwo = (RadioButton) findViewById(R.id.gRtwo);
        RadioButton answerThree = (RadioButton) findViewById(R.id.gRthree);
        RadioButton answerFour = (RadioButton) findViewById(R.id.gRfour);

        answerOne.setChecked(false);
        answerTwo.setChecked(false);
        answerThree.setChecked(false);
        answerFour.setChecked(false);

        answerOne.setBackgroundColor(getResources().getColor(R.color.transparent));
        answerTwo.setBackgroundColor(getResources().getColor(R.color.transparent));
        answerThree.setBackgroundColor(getResources().getColor(R.color.transparent));
        answerFour.setBackgroundColor(getResources().getColor(R.color.transparent));

        if (id == 0) {

        } else {
            if (id == 1) {
                answerOne.setChecked(true);
            } else if (id == 2) {
                answerTwo.setChecked(true);

            } else if (id == 3) {
                answerThree.setChecked(true);

            } else if (id == 4) {
                answerFour.setChecked(true);

            } else {
            }

            int correctAnswers = cursor.getInt(6);

            if (correctAnswers == id) {
//                Log.d("GuessActivity", "Answer is correct");

            } else {
                if (correctAnswers == 1) {
                    answerOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                if (correctAnswers == 2) {
                    answerTwo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                if (correctAnswers == 3) {
                    answerThree.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                if (correctAnswers == 4) {
                    answerFour.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }
    }
}

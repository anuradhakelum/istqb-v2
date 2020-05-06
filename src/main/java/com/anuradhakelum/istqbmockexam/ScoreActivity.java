package com.anuradhakelum.istqbmockexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Iterator;
import java.util.Map;


public class ScoreActivity extends AppCompatActivity {
    MockExam mockExam = new MockExam();
    Map<Integer, SelectedQuestion> answerMap01 = MockExam.answerMap;
    int flagNumbers = 0;
    int incorectAnswers = 0;
    int correctAnswers = 0;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2500224556487339/2387978864");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        getScore();
        animation();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void animation() {
        NumberProgressBar numberProgressBar = (NumberProgressBar) findViewById(R.id.oneone);

        numberProgressBar.setReachedBarColor(R.color.black);

        TextView NotAnswered = (TextView) findViewById(R.id.notAnsweredTxt);
        TextView incorrectAnsweredTxt = (TextView) findViewById(R.id.incorrectAnsweredTxt);
        TextView correctAnsweredTxt = (TextView) findViewById(R.id.correctAnsweredTxt);

        ProgressBar NotAnsweredBar = (ProgressBar) findViewById(R.id.notAnswered);
        ProgressBar IncorrectAnswerBar = (ProgressBar) findViewById(R.id.incorrectAnswer);
        ProgressBar CorrectAnswerBar = (ProgressBar) findViewById(R.id.correctAnswers);


        NotAnswered.setText("NOT ANSWERED = " + flagNumbers);
        incorrectAnsweredTxt.setText("INCORRECT ANSWERS = " + incorectAnswers);
        correctAnsweredTxt.setText("CORRECT ANSWERS = " + correctAnswers);

        NotAnsweredBar.setProgress(flagNumbers * 100 / 40);
        IncorrectAnswerBar.setProgress(incorectAnswers * 100 / 40);
        CorrectAnswerBar.setProgress(correctAnswers * 100 / 40);

        int marks = correctAnswers * 100 / 40;

//        Log.d("ScoreActivity", "" + marks);

        TextView score = (TextView) findViewById(R.id.textView6);
        TextView scoreText = (TextView) findViewById(R.id.textview07);

        numberProgressBar.setProgress(marks);

        score.setText("" + marks + "%");
        if (marks > +65) {
            score.setTextColor(getResources().getColor(R.color.colorAccent));
            scoreText.setText(getResources().getString(R.string.exam_passed));
        } else {
            score.setTextColor(getResources().getColor(R.color.red));
            scoreText.setText(getResources().getString(R.string.exam_FAILED));
        }


    }

    public void getScore() {
//        Log.d("ScoreActivity", "getScore started");

        Iterator it = this.answerMap01.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
//            Log.d("Map Item", pair.getKey() + " = " + pair.getValue());
            SelectedQuestion selectedQuestion = (SelectedQuestion) pair.getValue();
//            Log.d("ScoreActivity", "" + selectedQuestion.getCorrectAnswerID());

            if (selectedQuestion.getSelectedAnswerID() == 0) {
                flagNumbers++;
//                Log.d("ScoreActivity", "Flagged Question");
            } else {
                if (selectedQuestion.getSelectedAnswerID() == selectedQuestion.getCorrectAnswerID()) {
                    correctAnswers++;
//                    Log.d("ScoreActivity", "Correct answers");
                } else {
                    incorectAnswers++;
//                    Log.d("ScoreActivity", "Incorrect answers");
                }
            }
            //it.remove(); // avoids a ConcurrentModificationException

        }
//        Log.d("ScoreActivity", "Total flagged = " + flagNumbers + ", Total Correct = " + correctAnswers + ", " +
//                "Total Incorrect = " + incorectAnswers + " Number of items in Mock map "
//                + MockExam.answerMap.size() + " Number of items in this map " + this.answerMap01.size());
    }

    public void viewResult(View view) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


        try {
            Intent intent = new Intent(this, AnswersListView.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToHome(View view) {
        this.finish();

    }


}

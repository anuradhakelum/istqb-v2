package com.anuradhakelum.istqbmockexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MockExam extends AppCompatActivity {

    static Map<Integer, SelectedQuestion> answerMap = new HashMap<>();
    int chapterNumber = 0;
    QuestionSet questionSet;
    String DEBUG_TAG = "ISTQB";
    SelectedQuestion selectedQuestion;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Cursor cursor;
    private int question = 0;

    public int getQuest() {
        return question;
    }

    public void setQuest(int question) {
        this.question = question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_exam);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                chapterNumber = 0;
            } else {
                chapterNumber = extras.getInt("chapter");
            }
        }

        getQuestion();
        timecounting();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //answerMap = new HashMap<>();
    }

    public void timecounting() {
        final TextView timer = (TextView) findViewById(R.id.timers);
        new CountDownTimer(3600000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + String.format("%d : %d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                timer.setGravity(Gravity.CENTER);
            }

            public void onFinish() {
                timer.setText("Done!");
                timer.setTextColor(Color.RED);
            }
        }.start();
    }

    private void getQuestion() {
        databaseHelper.openDatabase();
        cursor = databaseHelper.getQuestionChapterMock(chapterNumber);
        if (cursor == null) {
            Log.d("ISTQB", "Cursor is null");
        }
        databaseHelper.close();
        if (cursor.getCount() > 1) {
            Log.d("Exam2Activity.class", "cursor count is OK " + cursor.getCount());
            setQuestions();
        } else {
            Log.d("Exam2Activity.class", "cursor count isn't OK " + cursor.getCount());
        }
    }

    //set question and answers
    private void setQuestions() {
        question++;
        if (question == 1) {
            cursor.moveToFirst();
        } else {
            cursor.moveToNext();
        }
//        Log.d("ISTQB", "Cursor position " + cursor.getPosition() + "Question ID " + question);
        resetRadioButtons(0);
        //Log.d("Question ID : " + cursor.getInt(0), " Answer is : " + cursor.getInt(6));
        questionSet = new QuestionSet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));
        setQuestionText(questionSet.getQuestion());
        setRadioButtonText(questionSet.getAnswerOne(), questionSet.getAnswerTwo(), questionSet.getAnswerThree(), questionSet.getAnswerFour());

        //ToDO
//        Log.d("MockExam", "" + questionSet.getCorrectAnswer());

        //Log.d("Question ID : " + cursor.getInt(0), " Answer is : " + cursor.getInt(6));

        //set Progress bar;
        setProgressBar();
        selectedQuestion = new SelectedQuestion(cursor.getInt(0), cursor.getInt(6));
        if (answerMap.get(question) == null) {
            answerMap.put(question, selectedQuestion);
            Log.d("ISTQB", "Entry added to map " + question);
        } else {
            selectedQuestion = answerMap.get(question);
            resetRadioButtons(selectedQuestion.getSelectedAnswerID());
            if (selectedQuestion.isFlag()) {

                ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
                imageButton.setImageDrawable(this.getResources().getDrawable(R.drawable.flag_enabled));
            }
        }
        //cursor.moveToNext();

//        Log.d("ISTQB", "" + answerMap.keySet() + "" + answerMap.get(question).getSelectedAnswerID());
    }

    private void setPrevQuestions() {
        question--;
        cursor.moveToPrevious();
        //cursor.moveToPrevious();
//        Log.d("Question ID : " + cursor.getInt(0), " Answer is : " + cursor.getInt(6));
        questionSet = new QuestionSet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getInt(6));
        setQuestionText(questionSet.getQuestion());
        setRadioButtonText(questionSet.getAnswerOne(), questionSet.getAnswerTwo(), questionSet.getAnswerThree(), questionSet.getAnswerFour());

        //ToDo
//        Log.d("MockExam", "" + questionSet.getCorrectAnswer());

//        Log.d("Question ID : " + cursor.getInt(0), " Answer is : " + cursor.getInt(6));
        selectedQuestion = new SelectedQuestion(cursor.getInt(0), cursor.getInt(6));
        if (answerMap.get(question) == null) {
            answerMap.put(question, selectedQuestion);
//            Log.d("ISTQB", "Entry added to map " + question);
        }
        //set Progress bar
        //setQuest(getQuest()-1);
        selectedQuestion = new SelectedQuestion(cursor.getInt(0), cursor.getInt(6));
        if (answerMap.get(question) == null) {
            answerMap.put(question, selectedQuestion);
//            Log.d("ISTQB", "Entry added to map " + question);
        } else {
            selectedQuestion = answerMap.get(question);
            resetRadioButtons(selectedQuestion.getSelectedAnswerID());
            if (selectedQuestion.isFlag()) {

                ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
                imageButton.setImageDrawable(this.getResources().getDrawable(R.drawable.flag_enabled));
            }
        }
        setProgressBar();
    }

    //set question
    private void setQuestionText(String questionText) {

        TextView questionTextView = (TextView) findViewById(R.id.question);
        questionTextView.setText(questionText);
    }

    //Set radio button text
    private void setRadioButtonText(String firstRadio, String secondRadio, String thridRadio, String fourthRadio) {

        RadioButton answerOne = (RadioButton) findViewById(R.id.Rone);
        RadioButton answerTwo = (RadioButton) findViewById(R.id.Rtwo);
        RadioButton answerThree = (RadioButton) findViewById(R.id.Rthree);
        RadioButton answerFour = (RadioButton) findViewById(R.id.Rfour);

        answerOne.setText(firstRadio);
        answerTwo.setText(secondRadio);
        answerThree.setText(thridRadio);
        answerFour.setText(fourthRadio);
    }

    public void setProgressBar() {

        Double questionNumber = Double.valueOf(getQuest());
        Double progressBarValue = questionNumber / 40 * 100;

//        Log.d("ISTQB", "ProgressBar Value " + progressBarValue);

        //set progress bar
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(progressBarValue.intValue());

        //set question number
        TextView textView = (TextView) findViewById(R.id.numberqu);
        textView.setText(getQuest() + " out of 40");
    }

    public void prevBtnClick(View view) {
        if (getQuest() == 40) {
            Button button = (Button) findViewById(R.id.Nextbtn);
            button.setText("NEXT");
        }
        if (getQuest() == 1) {
            Snackbar.make(view, "This is the first question".toUpperCase(), Snackbar.LENGTH_SHORT).show();
        }

        if (getQuest() > 1) {
            setPrevQuestions();

        }

    }

    public void nxtbtnClick(View view) {
        if (getQuest() == 39) {
            Button button = (Button) findViewById(R.id.Nextbtn);
            button.setText("SUBMIT");
        }

        if (getQuest() == 40) {

            int flagNumbers = 0;
            int notanswered = 0;

            Iterator it = this.answerMap.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                SelectedQuestion selectedQuestion = (SelectedQuestion) pair.getValue();

                //count flagged question and not answers questions
                if (selectedQuestion.isFlag()) {
                    flagNumbers++;
                }
                if (selectedQuestion.getSelectedAnswerID() == 0) {
                    notanswered++;
                }
            }

            //Alert Dialog
            android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Are you sure?");

            if (flagNumbers != 0 && notanswered != 0) {

                builder.setMessage("You haven't answer to " +
                        "" + notanswered + " question(s) and you have flagged " + flagNumbers + " question(s)");
            } else {
                if (flagNumbers != 0) {
                    builder.setMessage("You have flagged " + flagNumbers + " question(s)");
                }
                if (notanswered != 0) {
                    builder.setMessage("You haven't answer to " + notanswered + " question(s)");
                }
            }
            builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    yes();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setIcon(R.drawable.warning).show();

//            Log.d("MockExam", "Flag : " + flagNumbers + " Not Annswerd: " + notanswered);

            //set questions
        } else if (getQuest() < 40) {
            setQuestions();
        }

//        Log.d("ISTQB", "Question is " + question);
    }

    //reset radio buttons
    private void resetRadioButtons(int id) {

        ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
        if (id == 0) {
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.flag_disable));
        }

        RadioButton answerOne = (RadioButton) findViewById(R.id.Rone);
        RadioButton answerTwo = (RadioButton) findViewById(R.id.Rtwo);
        RadioButton answerThree = (RadioButton) findViewById(R.id.Rthree);
        RadioButton answerFour = (RadioButton) findViewById(R.id.Rfour);

        answerOne.setChecked(false);
        answerTwo.setChecked(false);
        answerThree.setChecked(false);
        answerFour.setChecked(false);

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
    }

    public void radioOneClick(View view) {
        resetRadioButtons(1);
        selectedQuestion = answerMap.get(question);
        selectedQuestion.setSelectedAnswerID(1);
        answerMap.remove(question);
        answerMap.put(question, selectedQuestion);
    }

    public void radioTwoClick(View view) {
        resetRadioButtons(2);
        selectedQuestion = answerMap.get(question);
        selectedQuestion.setSelectedAnswerID(2);
        answerMap.remove(question);
        answerMap.put(question, selectedQuestion);
    }

    public void radioThreeClick(View view) {
        resetRadioButtons(3);
        selectedQuestion = answerMap.get(question);
        selectedQuestion.setSelectedAnswerID(3);
        answerMap.remove(question);
        answerMap.put(question, selectedQuestion);
    }

    public void radioFourClick(View view) {
        resetRadioButtons(4);
        selectedQuestion = answerMap.get(question);
        selectedQuestion.setSelectedAnswerID(4);
        answerMap.remove(question);
        answerMap.put(question, selectedQuestion);
    }

    public void flagButtonClick(View view) {
//        Log.d("ISTQB", "Image button pressed");
        ImageView imageButton = findViewById(R.id.imageButton);
        selectedQuestion = answerMap.get(question);
        answerMap.remove(question);
        if (selectedQuestion.isFlag()) {
            selectedQuestion.setFlag(false);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.flag_disable));
//            Log.d("MockExam", "Change the flag false");
        } else {
            selectedQuestion.setFlag(true);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.flag_enabled));
//            Log.d("MockExam", "Change the flag true");
        }
        answerMap.put(question, selectedQuestion);
    }

    private void yes() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void reportbuttonatipn(View view) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"anuradhaut@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback : Question");
        i.putExtra(Intent.EXTRA_TEXT, "Question: "+questionSet.getQuestion());
        try {
            startActivity(Intent.createChooser(i, "Report problem...!"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MockExam.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

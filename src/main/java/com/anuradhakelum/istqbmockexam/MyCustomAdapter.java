package com.anuradhakelum.istqbmockexam;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by anuradhawijetunga on 2/16/18.
 */

public class MyCustomAdapter extends BaseAdapter {


    private DatabaseHelper databaseHelper;
    Context context;
    Map<Integer, SelectedQuestion> answerMap;
    LayoutInflater layoutInflater;
    Cursor cursor;

    public MyCustomAdapter(Context context, Map<Integer, SelectedQuestion> answerMap) {
        this.context = context;
        this.answerMap = answerMap;
    }

    @Override
    public int getCount() {
        return answerMap.size();
    }

    @Override
    public Object getItem(int i) {
        return answerMap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
//        Log.d("Veiw ID=", "" + i);

        databaseHelper = new DatabaseHelper(context);

        if (view == null) {
            layoutInflater = LayoutInflater.from(this.context);
            view = layoutInflater.inflate(R.layout.my_adapter_item, null);
            viewHolder = new ViewHolder();

            viewHolder.Qnum = (TextView) view.findViewById(R.id.qnumber_row);
            viewHolder.QID = (TextView) view.findViewById(R.id.questionNumber_row);
            viewHolder.Sans = (TextView) view.findViewById(R.id.selectedAnswer_row);
            viewHolder.Cans = (TextView) view.findViewById(R.id.correctAnswer_row);
            viewHolder.flg = (TextView) view.findViewById(R.id.flag_row);
            viewHolder.img = (ImageView) view.findViewById(R.id.imageView01);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SelectedQuestion selectedQuestion = (SelectedQuestion) getItem(i + 1);
//        Log.d("MyCustomAdapter", "" + selectedQuestion.questionID);
//        Log.d("MyCustomAdapter", "Question ID is :" + selectedQuestion.getQuestionID() + " View= " + i);

        int temp = selectedQuestion.getQuestionID();
        databaseHelper.openDatabase();
        cursor = databaseHelper.getQuestionSet(temp);
        databaseHelper.close();

        viewHolder.Qnum.setText((i + 1) + ". ");
        viewHolder.QID.setText(cursor.getString(1));
        viewHolder.Cans.setText(cursor.getString(cursor.getInt(6) + 1));
        viewHolder.Sans.setText("" + setSelectedAnswer(selectedQuestion.getSelectedAnswerID()));


        if (selectedQuestion.getSelectedAnswerID() == selectedQuestion.getCorrectAnswerID()) {
            viewHolder.img.setBackgroundResource(R.drawable.check);
            viewHolder.flg.setText("Answer is correct.");
        } else {
            viewHolder.img.setBackgroundResource(R.drawable.close);
            viewHolder.flg.setText("Answer is incorrect.");
        }

        return view;
    }

    private String setSelectedAnswer(int i) {
        String returnText = "No answer";

        if (i == 0) {
            returnText = "You haven't answer to this question";
        } else {
            returnText = cursor.getString(i + 1);
        }

        return returnText;
    }

    private class ViewHolder {

        TextView Qnum;
        TextView QID;
        TextView Cans;
        TextView Sans;
        TextView flg;
        ImageView img;
    }


}

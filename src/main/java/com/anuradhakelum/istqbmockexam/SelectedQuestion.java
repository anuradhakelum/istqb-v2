package com.anuradhakelum.istqbmockexam;

/**
 * Created by anuradhawijetunga on 1/27/18.
 */

public class SelectedQuestion {
    int questionID;
    int correctAnswerID;
    int selectedAnswerID;
    boolean flag;

    public SelectedQuestion(int questionID, int correctAnswerID) {
        this.questionID = questionID;
        this.correctAnswerID = correctAnswerID;
        this.selectedAnswerID = 0;
        this.flag = false;

    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getCorrectAnswerID() {
        return correctAnswerID;
    }

    public void setCorrectAnswerID(int correctAnswerID) {
        this.correctAnswerID = correctAnswerID;
    }

    public int getSelectedAnswerID() {
        return selectedAnswerID;
    }

    public void setSelectedAnswerID(int selectedAnswerID) {
        this.selectedAnswerID = selectedAnswerID;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

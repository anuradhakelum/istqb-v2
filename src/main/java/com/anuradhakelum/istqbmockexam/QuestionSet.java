package com.anuradhakelum.istqbmockexam;

/**
 * Created by anuradhawijethunga on 10/15/16.
 */

public class QuestionSet {

    private int questionID;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private int correctAnswer;

    public int getQuestionID() {
        return questionID;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


    public QuestionSet(int questionID, String question, String answerOne, String answerTwo, String answerThree, String answerFour, int correctAnswer) {
        this.questionID = questionID;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswer;
    }
}

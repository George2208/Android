package com.example.quizz.ui.home;

import android.util.Pair;
import android.widget.TextView;

import java.util.List;

public class QuestionModel {
    private final String question;
    private final List<Pair<String, Boolean>> answers;
    private final TextView score;

    public QuestionModel(String question, List<Pair<String, Boolean>>  answers, TextView score) {
        this.question = question;
        this.answers = answers;
        this.score = score;
    }
    public List<Pair<String, Boolean>> getAnswers() { return answers; }
    public String getQuestion() { return question; }
    public TextView getScore() { return score; }
}
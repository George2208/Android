package com.example.quizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz.db";
    private static Database instance;

    private Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.getWritableDatabase().execSQL("delete from questions");
        this.getWritableDatabase().execSQL("delete from answers");
        this.addQuestion(1, "In which year Java appeared?", Arrays.asList(
                new Pair<>("1995", true),
                new Pair<>("1900", false),
                new Pair<>("2001", false)
        ));
        this.addQuestion(2, "Which languages are object oriented?", Arrays.asList(
                new Pair<>("Javascript", true),
                new Pair<>("C#", true),
                new Pair<>("Python", true),
                new Pair<>("Java", true)
        ));
        this.addQuestion(3, "Which was the most used language in 2018?", Arrays.asList(
                new Pair<>("C++", false),
                new Pair<>("Prolog", false),
                new Pair<>("Python", true)
        ));
        this.addQuestion(4, "Which is the best language programming?", Arrays.asList(
                new Pair<>("Javascript", false),
                new Pair<>("It depends on the context", true),
                new Pair<>("Haskell", false),
                new Pair<>("R", false)
        ));
        this.addQuestion(5, "What is a static variable?", Arrays.asList(
                new Pair<>("A local object variable", false),
                new Pair<>("A shared object variable", true)
        ));
    }
    public static void init(Context context) { instance = new Database(context); }
    public static Database getInstance() { return instance; }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table questions(question_id INTEGER primary key, question TEXT)");
        sqLiteDatabase.execSQL("create table answers(answer_id INTEGER primary key autoincrement, question_id INTEGER references questions(id), answer TEXT, value BOOL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int _1, int _2) {
        sqLiteDatabase.execSQL("drop table if exists questions");
        sqLiteDatabase.execSQL("drop table if exists answers");
        onCreate(sqLiteDatabase);
    }

    public void addQuestion(int id, String question, List<Pair<String, Boolean>> answers) {
        ContentValues questionEntry = new ContentValues();
        questionEntry.put("question_id", id);
        questionEntry.put("question", question);
        this.getWritableDatabase().insert("questions", null, questionEntry);
        for(Pair<String, Boolean> answer: answers) {
            ContentValues answerEntry = new ContentValues();
            answerEntry.put("question_id", id);
            answerEntry.put("answer", answer.first);
            answerEntry.put("value", answer.second);
            this.getWritableDatabase().insert("answers", null, answerEntry);
        }
    }

    public static List<Pair<String, List<Pair<String, Boolean>>>> test() {
        List<Pair<String, List<Pair<String, Boolean>>>> questions = new ArrayList<>();
        String questionText = "question 1";
        List<Pair<String, Boolean>> answers = new ArrayList<>();
        answers.add(new Pair<>("answer 1", false));
        answers.add(new Pair<>("answer 2", true));
        answers.add(new Pair<>("answer 3", false));
        answers.add(new Pair<>("answer 3", false));
        questions.add(new Pair<>(questionText, answers));
        questions.add(new Pair<>("question 2", answers));
        return questions;
    }

    public List<Pair<String, List<Pair<String, Boolean>>>> getQuestions() {
        List<Pair<String, List<Pair<String, Boolean>>>> questions = new ArrayList<>();
        Cursor cursorQuestions = this.getWritableDatabase().rawQuery("select * from questions", null);
        if (cursorQuestions.moveToFirst()) {
            do {
                String question_id = String.valueOf(cursorQuestions.getInt(Math.min(1, cursorQuestions.getColumnIndex("question_id"))));
                String question = cursorQuestions.getString(Math.min(1, cursorQuestions.getColumnIndex("question")));
                List<Pair<String, Boolean>> answers = new ArrayList<>();
                Cursor cursorAnswers = this.getWritableDatabase().rawQuery("select * from answers where question_id="+question_id, null);
                if (cursorAnswers.moveToFirst()) {
                    do {
                        String answer = cursorAnswers.getString(2);
                        Boolean value = 0 != cursorAnswers.getInt(3);
                        answers.add(new Pair<>(answer, value));
                    } while (cursorAnswers.moveToNext());
                }
                cursorAnswers.close();
                questions.add(new Pair<>(question, answers));
            } while (cursorQuestions.moveToNext());
        }
        cursorQuestions.close();
        return questions;
    }
}
package com.example.quizz.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz.Database;
import com.example.quizz.MainActivity;
import com.example.quizz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdaptor extends RecyclerView.Adapter<QuestionAdaptor.ViewHolder> {
    private ArrayList<QuestionModel> courseModalArrayList;
    private final List<Pair<String, List<Pair<String, Boolean>>>> questions = Database.test();
    private final Context context;
    public static int solvedQuestions = 0;
    public static int wrongAnswers = 0;

    public QuestionAdaptor(ArrayList<QuestionModel> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<QuestionModel> filterlist) {
        courseModalArrayList = filterlist;
        this.notifyDataSetChanged();
    }

    public QuestionAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdaptor.ViewHolder holder, int position) {
        QuestionModel modal = courseModalArrayList.get(position);
        holder.question.setText(modal.getQuestion());
        holder.answers.removeAllViews();
        List<Pair<String, Boolean>> answers = modal.getAnswers();
        TextView score = modal.getScore();
        for(Pair<String, Boolean> answer: answers) {
            Button button = new Button(context);
            button.setText(answer.first);
            holder.answers.addView(button);

            button.setOnClickListener(view -> {
                if(answer.second) {
                    solvedQuestions += 1;
                    button.setBackgroundColor(Color.rgb(116, 225, 99));
                }
                else {
                    wrongAnswers += 1;
                    button.setBackgroundColor(Color.rgb(255, 117, 90));
                }
                String s = "Score: " + (solvedQuestions - wrongAnswers);
                score.setText(s);
                button.setClickable(false);
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 0);
            button.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() { return courseModalArrayList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView question;
        private final LinearLayout answers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answers =  itemView.findViewById(R.id.answers);
        }
    }
}
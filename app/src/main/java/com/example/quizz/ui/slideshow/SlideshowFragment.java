package com.example.quizz.ui.slideshow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizz.MainActivity;
import com.example.quizz.R;
import com.example.quizz.SecondActivity;
import com.example.quizz.databinding.FragmentSlideshowBinding;
import com.example.quizz.ui.home.QuestionAdaptor;

public class SlideshowFragment extends Fragment {
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView goodAnswers = root.findViewById(R.id.goodAnswers);
        TextView wrongAnswers = root.findViewById(R.id.wrongAnswers);
        String score1 = "Correct answers: " + QuestionAdaptor.solvedQuestions;
        String score2 = "Wrong answers: " + QuestionAdaptor.wrongAnswers;
        goodAnswers.setText(score1);
        wrongAnswers.setText(score2);

        root.findViewById(R.id.backButton).setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        });

        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(linearLayout, "alpha", .7f, .1f);
        fadeOut.setDuration(1500);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(linearLayout, "alpha", .1f, .7f);
        fadeIn.setDuration(1500);

        animatorSet.play(fadeIn).after(fadeOut);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
        animatorSet.start();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
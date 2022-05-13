package com.example.quizz.ui.slideshow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
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
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        LinearLayout linearLayout = root.findViewById(R.id.linearLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(linearLayout, "alpha", .7f, .1f);
        fadeOut.setDuration(1200);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(linearLayout, "alpha", .1f, .7f);
        fadeIn.setDuration(1200);

        animatorSet.play(fadeIn).after(fadeOut);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
        animatorSet.start();

        WebView mWebview = root.findViewById(R.id.webView);
        String video = "<body style=\"margin: 0; padding: 0\"> <iframe width=\"100%\" height=\"100%\" \" src=\"https://www.youtube.com/embed/n9v-2xF54HM\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body>";
        mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.getSettings().setJavaScriptEnabled(true);
        class Autoplay extends WebViewClient {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                long downTime = SystemClock.uptimeMillis();
                long eventTime = downTime + 100;
                int x = view.getWidth() / 2, y = view.getHeight() / 2;
                view.dispatchTouchEvent(MotionEvent.obtain
                       (downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0));
                view.dispatchTouchEvent(MotionEvent.obtain
                        (downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0));
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        () -> root.findViewById(R.id.textView2).setBackgroundColor(Color.TRANSPARENT),
                        6000);
            }
        }
        mWebview.setWebViewClient(new Autoplay());
        mWebview.loadData(video, "text/html", "UTF-8");
        root.findViewById(R.id.textView2).setOnTouchListener((v, event) -> true);
            return root;
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
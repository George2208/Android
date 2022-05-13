package com.example.quizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.quizz.databinding.ActivityMainBinding;
import com.example.quizz.databinding.FragmentSlideshowBinding;
import com.example.quizz.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;


public class SecondActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_slideshow);
        getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content,  new SlideshowFragment()).commit();
    }
}
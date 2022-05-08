package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.quizz.ui.slideshow.SlideshowFragment;

import org.w3c.dom.Text;


public class SecondActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlideshowFragment sfr =  new SlideshowFragment();
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, sfr).commit();

        Intent i = new Intent(SecondActivity.this, SecondActivity.class);
        startActivity(i);
    }
}
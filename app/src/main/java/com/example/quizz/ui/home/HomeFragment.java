package com.example.quizz.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz.Database;
import com.example.quizz.R;
import com.example.quizz.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private QuestionAdaptor mAdapter;
    private final ArrayList<QuestionModel> mList;
    static int direction;

    public HomeFragment() {
        super();
        mList = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout div = root.findViewById(R.id.div);
        RecyclerView mRecyclerView = root.findViewById(R.id.recycleView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    div.setVisibility(direction <= 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    direction = dy;
            }
        });

        mRecyclerView.setHasFixedSize(true);
        for (Pair<String, List<Pair<String, Boolean>>> i : Database.getInstance().getQuestions()) {
            mList.add(new QuestionModel(i.first, i.second, root.findViewById(R.id.score)));
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new QuestionAdaptor(mList, getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        EditText editText = root.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<QuestionModel> fL = new ArrayList<>();
                for (QuestionModel s : mList) {
                    if(s.getQuestion().toLowerCase().contains(editable.toString().toLowerCase()))
                        fL.add(s);
                }
                mAdapter.filterList(fL);
            }
        });
        return root;
    }
}
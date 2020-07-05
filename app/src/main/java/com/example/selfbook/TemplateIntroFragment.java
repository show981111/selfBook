package com.example.selfbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemplateIntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemplateIntroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String templateIntro;

    public TemplateIntroFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TemplateIntroFragment newInstance(String templateIntro) {
        TemplateIntroFragment fragment = new TemplateIntroFragment();
        Bundle args = new Bundle();
        args.putString("templateIntro", templateIntro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            templateIntro = getArguments().getString("templateIntro");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_template_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_templateIntro = view.findViewById(R.id.tv_templateIntro);
        tv_templateIntro.setText(templateIntro);
    }
}

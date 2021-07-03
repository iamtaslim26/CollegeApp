package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kgec.collegeadminapp.R;

public class AboutFragment extends Fragment {

    private View about_view;

    public AboutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        about_view= inflater.inflate(R.layout.fragment_about2, container, false);
        return about_view;
    }
}
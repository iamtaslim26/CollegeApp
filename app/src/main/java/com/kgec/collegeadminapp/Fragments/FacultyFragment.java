package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kgec.collegeadminapp.R;

public class FacultyFragment extends Fragment {

    private View faculty_view;


    public FacultyFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        faculty_view= inflater.inflate(R.layout.fragment_faculty, container, false);

        return faculty_view;
    }
}
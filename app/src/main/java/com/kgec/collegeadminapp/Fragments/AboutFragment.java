package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kgec.collegeadminapp.R;
import com.kgec.collegeadminapp.Users.BranchAdapter;
import com.kgec.collegeadminapp.Users.BranchModel;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private View about_view;

    private ViewPager viewPager;
    private BranchAdapter branchAdapter;
    private List<BranchModel>list;

    public AboutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        about_view= inflater.inflate(R.layout.fragment_about2, container, false);

        list=new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_computer,"Computer Science","Dear all, I am glad to inform that, Computer Science and Engineering is one of the major disciplines being offered in this college since the outset of this college, in the year 1995, with approval from the All India Council for Technical Education, New Delhi, India. Two courses are offered in the department: Undergraduate (B.E.) and Postgraduate (M.Tech). The strength of the department is it’s faculty members. The department has a strong group of 17 faculty members with a balanced combination of experienced as well as young blood. The department is equipped with a number of laboratories to cater the need of laboratory courses. All the faculty members are engaged in active research work and can ably guide the students in the classes and project works. A good number of sponsored projects are either carried out or being executed at present. Faculty members also publish good quality research papers frequently. Our students are our real strength. Incumbents in our department are top quality students admitted through WBJEE merit list. In past academic years, all of our students have been picked up by reputed companies. We have excellent network facility in the department also. Hope, the Department will continue to show excellence in future."));
        list.add(new BranchModel(R.drawable.ic_mech,"Mechanical Engineering","The Department of Mechanical Engineering has taken a lead role in the college since its inception in the year of 1995. The Department has taken active role in upgrading the academic credentials of the college by introducing the first Master of Technology course in Production Engineering in this college in 2003. The department is also chosen as a Centre by the Institution of Engineers (India) for conducting laboratory classes towards AMIE examination. Two National Conferences were successfully organized by the Department in 2003 and in 2007, apart from organizing a number of Seminars, Workshops, Invited Lecturers, Training Courses, etc. for academic improvement of teachers, students and staff of this college, and also for other colleges around. Out of the quality Project works at the UG, PG and Ph.D level offered in the Department, good number of publications have been made in National and International Journals and Conferences to indicate its high standard. The Department of Mechanical Engineering is also undertaking testing and consultancy works regularly as per need of the industry."));
        list.add(new BranchModel(R.drawable.buttons,"Information Technology","Information technology (IT) is the use of any computers, storage, networking and other physical devices, infrastructure and processes to create, process, store, secure and exchange all forms of electronic data. ... The commercial use of IT encompasses both computer technology and telecommunications."));

        branchAdapter=new BranchAdapter(getContext(),list);
        viewPager=about_view.findViewById(R.id.view_pager);
        viewPager.setAdapter(branchAdapter);


        return about_view;
    }
}
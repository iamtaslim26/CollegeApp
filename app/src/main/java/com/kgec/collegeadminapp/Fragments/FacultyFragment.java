package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgec.collegeadminapp.Admin.model.AddTeacher;
import com.kgec.collegeadminapp.Faculty.AddTeachersAdapter;
import com.kgec.collegeadminapp.Faculty.UpdateFacultyActivity;
import com.kgec.collegeadminapp.R;
import com.kgec.collegeadminapp.Users.ShowTeachersAdapter;

import java.util.ArrayList;

public class FacultyFragment extends Fragment {

    private View faculty_view;
    private RecyclerView cs_recyclerview,it_recyclerview,ee_recyclerview,me_recyclerview,ece_recyclerview;

    private LinearLayout cs_linear_layout,it_linear_layout,ee_linear_layout,me_linear_layout,ece_linear_layout;

    private ArrayList<AddTeacher> list_Cs,list_it,list_ee,list_me,list_ece;
    private ShowTeachersAdapter adapter;
    private DatabaseReference databaseReference;




    public FacultyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        faculty_view= inflater.inflate(R.layout.fragment_faculty, container, false);


        Initialize();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Teachers");

        CseDepartment();
        ItDepartment();
        EceDepartment();
        EEDepartment();
        MechanicalDepartment();


        return faculty_view;
    }

    private void MechanicalDepartment() {
        databaseReference.child("Mechanical Dept").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    me_linear_layout.setVisibility(View.VISIBLE);
                    me_recyclerview.setVisibility(View.GONE);

                }
                else {


                    me_linear_layout.setVisibility(View.GONE);
                    me_recyclerview.setVisibility(View.VISIBLE);

                    list_me=new ArrayList<>();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        AddTeacher addTeacher=snapshot.getValue(AddTeacher.class);
                        list_me.add(addTeacher);
                    }

                  adapter=new ShowTeachersAdapter(getContext(),list_me);
                    me_recyclerview.setHasFixedSize(true);
                    me_recyclerview.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void EEDepartment() {

        databaseReference.child("Electrical Engineering").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){

                    ee_linear_layout.setVisibility(View.VISIBLE);
                    ee_recyclerview.setVisibility(View.GONE);

                }
                else {


                    ee_linear_layout.setVisibility(View.GONE);
                    ee_recyclerview.setVisibility(View.VISIBLE);

                    list_ee=new ArrayList<>();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        AddTeacher addTeacher=snapshot.getValue(AddTeacher.class);
                        list_ee.add(addTeacher);
                    }

                    adapter=new ShowTeachersAdapter(getContext(),list_ee);
                    ee_recyclerview.setHasFixedSize(true);
                    ee_recyclerview.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void EceDepartment() {

        databaseReference.child("Ece dept").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){

                    ece_linear_layout.setVisibility(View.VISIBLE);
                    ece_recyclerview.setVisibility(View.GONE);
                }
                else {

                    ece_linear_layout.setVisibility(View.GONE);
                    ece_recyclerview.setVisibility(View.VISIBLE);

                    list_ece=new ArrayList<>();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        AddTeacher addTeacher=snapshot.getValue(AddTeacher.class);
                        list_ece.add(addTeacher);
                    }

                   adapter=new ShowTeachersAdapter(getContext(),list_ece);
                    ece_recyclerview.setHasFixedSize(true);
                    ece_recyclerview.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ItDepartment() {

        databaseReference.child("Information Technology").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){

                    it_recyclerview.setVisibility(View.GONE);
                    it_linear_layout.setVisibility(View.VISIBLE);

                }
                else {

                    list_it=new ArrayList<>();
                    it_recyclerview.setVisibility(View.VISIBLE);

                    it_linear_layout.setVisibility(View.GONE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        AddTeacher addTeacher=snapshot.getValue(AddTeacher.class);
                        list_it.add(addTeacher);
                    }

                    adapter=new ShowTeachersAdapter(getContext(),list_it);
                    it_recyclerview.setHasFixedSize(true);
                    it_recyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CseDepartment() {

        databaseReference.child("Computer Science").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list_Cs=new ArrayList<>();

                if (!dataSnapshot.exists()){

                    cs_recyclerview.setVisibility(View.GONE);
                    cs_linear_layout.setVisibility(View.VISIBLE);

                }
                else {

                    cs_recyclerview.setVisibility(View.VISIBLE);

                    cs_linear_layout.setVisibility(View.GONE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        AddTeacher addTeacher=snapshot.getValue(AddTeacher.class);
                        list_Cs.add(addTeacher);
                    }
                    cs_recyclerview.setHasFixedSize(true);
                   adapter=new ShowTeachersAdapter(getContext(),list_Cs);
                    cs_recyclerview.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Initialize() {

        cs_recyclerview=faculty_view.findViewById(R.id.cs_dept_fragment);
        it_recyclerview=faculty_view.findViewById(R.id.it_dept_fragment);
        ee_recyclerview=faculty_view.findViewById(R.id.ee_dept_fragment);
        me_recyclerview=faculty_view.findViewById(R.id.me_dept_fragment);
        ece_recyclerview=faculty_view.findViewById(R.id.ece_dept_fragment);

        cs_linear_layout=faculty_view.findViewById(R.id.cs_dept_linearlayout_fragment);
        it_linear_layout=faculty_view.findViewById(R.id.it_dept_linearlayout_fragment);
        ee_linear_layout=faculty_view.findViewById(R.id.ee_dept_linearlayout_fragment);
        me_linear_layout=faculty_view.findViewById(R.id.me_dept_linearlayout_fragment);
        ece_linear_layout=faculty_view.findViewById(R.id.ece_dept_linearlayout_fragment);

        cs_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        it_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ece_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ee_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        me_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
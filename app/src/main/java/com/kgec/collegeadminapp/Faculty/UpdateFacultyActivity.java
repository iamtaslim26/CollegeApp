package com.kgec.collegeadminapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgec.collegeadminapp.Admin.model.AddTeacher;
import com.kgec.collegeadminapp.R;

import java.util.ArrayList;

public class UpdateFacultyActivity extends AppCompatActivity {
    private FloatingActionButton fab_btn;

    private RecyclerView cs_recyclerview,it_recyclerview,ee_recyclerview,me_recyclerview,ece_recyclerview;

    private LinearLayout cs_linear_layout,it_linear_layout,ee_linear_layout,me_linear_layout,ece_linear_layout;

    private ArrayList<AddTeacher>list_Cs,list_it,list_ee,list_me,list_ece;
    private AddTeachersAdapter addTeachersAdapter;

    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        Initialize();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Teachers");

        fab_btn=findViewById(R.id.fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddTeachersActivity.class));
            }
        });

        CseDepartment();
        ItDepartment();
        EceDepartment();
        EEDepartment();
        MechanicalDepartment();
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

                    addTeachersAdapter=new AddTeachersAdapter(UpdateFacultyActivity.this,list_me,"Mechanical Dept");
                    me_recyclerview.setHasFixedSize(true);
                    me_recyclerview.setAdapter(addTeachersAdapter);

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

                    addTeachersAdapter=new AddTeachersAdapter(UpdateFacultyActivity.this,list_ee,"Electrical Engineering");
                    ee_recyclerview.setHasFixedSize(true);
                    ee_recyclerview.setAdapter(addTeachersAdapter);

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

                    addTeachersAdapter=new AddTeachersAdapter(UpdateFacultyActivity.this,list_ece,"Ece dept");
                    ece_recyclerview.setHasFixedSize(true);
                    ece_recyclerview.setAdapter(addTeachersAdapter);
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

                    addTeachersAdapter=new AddTeachersAdapter(UpdateFacultyActivity.this,list_it,"Information Technology");
                    it_recyclerview.setHasFixedSize(true);
                    it_recyclerview.setAdapter(addTeachersAdapter);
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
                    addTeachersAdapter=new AddTeachersAdapter(UpdateFacultyActivity.this,list_Cs,"Computer Science");
                    cs_recyclerview.setAdapter(addTeachersAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Initialize() {

        cs_recyclerview=findViewById(R.id.cs_dept);
        it_recyclerview=findViewById(R.id.it_dept);
        ee_recyclerview=findViewById(R.id.ee_dept);
        me_recyclerview=findViewById(R.id.me_dept);
        ece_recyclerview=findViewById(R.id.ece_dept);

        cs_linear_layout=findViewById(R.id.cs_dept_linearlayout);
        it_linear_layout=findViewById(R.id.it_dept_linearlayout);
        ee_linear_layout=findViewById(R.id.ee_dept_linearlayout);
        me_linear_layout=findViewById(R.id.me_dept_linearlayout);
        ece_linear_layout=findViewById(R.id.ece_dept_linearlayout);

        cs_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        it_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ece_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ee_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        me_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}
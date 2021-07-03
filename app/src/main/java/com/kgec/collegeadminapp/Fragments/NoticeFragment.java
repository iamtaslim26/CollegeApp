package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgec.collegeadminapp.R;
import com.kgec.collegeadminapp.Users.NoticeUsers;
import com.kgec.collegeadminapp.Users.NoticeUsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {

    private View notice_view;
    private RecyclerView recyclerView;

    private ArrayList<NoticeUsers>list;
    private DatabaseReference databaseReference;
    private NoticeUsersAdapter noticeUsersAdapter;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        notice_view= inflater.inflate(R.layout.fragment_notice, container, false);

        recyclerView=notice_view.findViewById(R.id.notice_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notice");

        list=new ArrayList<>();

        ShowNotice();
        return notice_view;
    }

    private void ShowNotice() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        NoticeUsers noticeUsers=snapshot.getValue(NoticeUsers.class);
                        list.add(noticeUsers);

                    }
                }

                noticeUsersAdapter=new NoticeUsersAdapter(getContext(),list);
                recyclerView.setAdapter(noticeUsersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
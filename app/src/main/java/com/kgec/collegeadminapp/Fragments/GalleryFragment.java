package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.kgec.collegeadminapp.Users.UsersGallery;
import com.kgec.collegeadminapp.Users.UsersGalleryAdapter;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private View gallery_view;

    private RecyclerView covocation_reyclerview;
    private RecyclerView others_reyclerview;
    private RecyclerView independeceday_reyclerview;
    private CardView convocation,others,independence;

    private ArrayList<UsersGallery>list;
    private DatabaseReference databaseReference;
    private UsersGalleryAdapter adapter;




    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        gallery_view= inflater.inflate(R.layout.fragment_gallery, container, false);

        covocation_reyclerview=gallery_view.findViewById(R.id.convocation_recyclerview);
        others_reyclerview=gallery_view.findViewById(R.id.others_recyclerview);
        independeceday_reyclerview=gallery_view.findViewById(R.id.independeceday_recyclerview);

        convocation=gallery_view.findViewById(R.id.con_card);
        others=gallery_view.findViewById(R.id.others_card);
        independence=gallery_view.findViewById(R.id.ind_card);

        covocation_reyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        others_reyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        independeceday_reyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Gallery");

        list=new ArrayList<>();


        ShowConvocation();

        return gallery_view;
    }

    private void ShowConvocation() {

        databaseReference.child("Convocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    convocation.setVisibility(View.VISIBLE);

                   // list.clear();

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        UsersGallery usersGallery=snapshot.getValue(UsersGallery.class);
                        list.add(usersGallery);
                    }
                }

                adapter=new UsersGalleryAdapter(getContext(),list);
                covocation_reyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
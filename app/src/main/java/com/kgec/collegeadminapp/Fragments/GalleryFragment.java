package com.kgec.collegeadminapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
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
import java.util.List;

public class GalleryFragment extends Fragment {

    private View gallery_view;

    private RecyclerView covocation_reyclerview;
    private RecyclerView others_reyclerview;
    private RecyclerView independeceday_reyclerview;
    private CardView convocation,others,independence;


    private DatabaseReference databaseReference;
    private UsersGalleryAdapter usersGalleryAdapter;





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

        covocation_reyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));

        independeceday_reyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        covocation_reyclerview.setHasFixedSize(true);
        others_reyclerview.setHasFixedSize(true);
        independeceday_reyclerview.setHasFixedSize(true);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Gallery");


        ShowConvocation();

        ShowOthersEvents();

        ShowIndependenceEvent();

        return gallery_view;
    }

    private void ShowIndependenceEvent() {

        List<UsersGallery>list=new ArrayList<>();

        databaseReference.child("Independence day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    independence.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        UsersGallery usersGallery=snapshot.getValue(UsersGallery.class);
                        list.add(usersGallery);
                    }
                }

                usersGalleryAdapter=new UsersGalleryAdapter(getContext(),list);
                independeceday_reyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                independeceday_reyclerview.setAdapter(usersGalleryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ShowOthersEvents() {

        List<UsersGallery>list=new ArrayList<>();

        databaseReference.child("Other Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    others.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        UsersGallery usersGallery=snapshot.getValue(UsersGallery.class);
                        list.add(usersGallery);
                    }
                }

                usersGalleryAdapter=new UsersGalleryAdapter(getContext(),list);
                others_reyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                others_reyclerview.setAdapter(usersGalleryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ShowConvocation() {

        List<UsersGallery>list=new ArrayList<>();


        databaseReference.child("Convocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    convocation.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        UsersGallery usersGallery=snapshot.getValue(UsersGallery.class);
                        list.add(usersGallery);
                    }
                }

                usersGalleryAdapter=new UsersGalleryAdapter(getContext(),list);
                covocation_reyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                covocation_reyclerview.setAdapter(usersGalleryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
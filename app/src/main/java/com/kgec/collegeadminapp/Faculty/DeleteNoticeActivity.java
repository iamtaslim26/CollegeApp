package com.kgec.collegeadminapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgec.collegeadminapp.Admin.model.Notice;
import com.kgec.collegeadminapp.Admin.model.NoticeAdapter;
import com.kgec.collegeadminapp.R;

import java.util.ArrayList;

public class DeleteNoticeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<Notice>list;
    private NoticeAdapter noticeAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        recyclerView=findViewById(R.id.show_notice_recyclerview);
        progressBar=findViewById(R.id.progress_delete_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notice");

        ShowNotice();
    }

    private void ShowNotice() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        Notice notice=snapshot.getValue(Notice.class);
                        list.add(notice);
                    }

                    noticeAdapter=new NoticeAdapter(DeleteNoticeActivity.this,list);
                    recyclerView.setAdapter(noticeAdapter);
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
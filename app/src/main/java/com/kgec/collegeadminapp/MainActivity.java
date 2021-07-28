package com.kgec.collegeadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kgec.collegeadminapp.Admin.DashBoardActivity;
import com.kgec.collegeadminapp.Admin.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button admin,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin=findViewById(R.id.admin_page);
        user=findViewById(R.id.user_page);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),UserMainActivity.class));
            }
        });
    }
}
package com.kgec.collegeadminapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgec.collegeadminapp.R;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText et_phone,et_password;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn=findViewById(R.id.admin_login_btn);
        et_phone=findViewById(R.id.admin_login_phonenumber);
        et_password=findViewById(R.id.admin_login_password);
        loadingbar=new ProgressDialog(this);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone=et_phone.getText().toString();
                String password=et_password.getText().toString();

                if (TextUtils.isEmpty(phone)  && TextUtils.isEmpty(password)){

                    Toast.makeText(LoginActivity.this, "Please fill the blank. . .", Toast.LENGTH_SHORT).show();
                }
                else {

                    loadingbar.setMessage("Authenticating. . . ");
                    loadingbar.show();

                    AllowUserToLogin(phone,password);
                }
            }
        });
    }

    private void AllowUserToLogin(String phone, String password) {

       final DatabaseReference RootRef;

        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Admins").child(phone).exists()){

                    Admins admins=dataSnapshot.child("Admins").child(phone).getValue(Admins.class);

                    if (admins.getPhone().equals(phone)){
                        if (admins.getPassword().equals(password)){

                            Toast.makeText(LoginActivity.this, "Login Successful. . .", Toast.LENGTH_SHORT).show();
                           SendUserToDashBoardActivity();
                            loadingbar.dismiss();
                        }
                        else {

                            loadingbar.dismiss();
                            Toast.makeText(LoginActivity.this, "Error Password or phone number", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendUserToDashBoardActivity() {

        Intent intent=new Intent(getApplicationContext(),DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
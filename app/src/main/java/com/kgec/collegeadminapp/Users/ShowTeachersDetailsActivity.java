package com.kgec.collegeadminapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

public class ShowTeachersDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView name_text,post_text,email_text;

    private String name,post,email,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teachers_details);


        imageView=findViewById(R.id.show_profile_image_teacher);
        name_text=findViewById(R.id.show_name_teacher);
        post_text=findViewById(R.id.show_post_teacher);
        email_text=findViewById(R.id.show_email_teacher);


        name=getIntent().getExtras().get("name").toString();
        post=getIntent().getExtras().get("post").toString();
        email=getIntent().getExtras().get("email").toString();
        image=getIntent().getExtras().get("image").toString();

        name_text.setText(name);
        email_text.setText(email);
        post_text.setText(post);
        Picasso.get().load(image).into(imageView);


    }
}
package com.kgec.collegeadminapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class FullImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imageView=findViewById(R.id.full_image1);
        image= getIntent().getExtras().get("image").toString();


        Picasso.get().load(image).into(imageView);
    }
}
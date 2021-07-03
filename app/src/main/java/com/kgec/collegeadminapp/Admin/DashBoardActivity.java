package com.kgec.collegeadminapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kgec.collegeadminapp.Admin.UploadImageActivity;
import com.kgec.collegeadminapp.Admin.UploadNoticeActivity;
import com.kgec.collegeadminapp.Admin.uploadPdfActivity;
import com.kgec.collegeadminapp.Faculty.DeleteNoticeActivity;
import com.kgec.collegeadminapp.Faculty.UpdateFacultyActivity;
import com.kgec.collegeadminapp.R;

public class DashBoardActivity extends AppCompatActivity {

    private LinearLayout upload_notice,upload_image,upload_pdf,upload_faculty,delete_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Initialize();

        upload_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadNoticeActivity.class));
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadImageActivity.class));
            }
        });
        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), uploadPdfActivity.class));
            }
        });

        upload_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), UpdateFacultyActivity.class));

            }
        });

        delete_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), DeleteNoticeActivity.class));
            }
        });
    }

    private void Initialize() {

        upload_notice=findViewById(R.id.upload_notice_layout);
        upload_image=findViewById(R.id.upload_image_layout);
        upload_pdf=findViewById(R.id.upload_pdf_layout);
        upload_faculty=findViewById(R.id.upload_faculty_layout);
        delete_notice=findViewById(R.id.delete_notice_layout);
    }
}
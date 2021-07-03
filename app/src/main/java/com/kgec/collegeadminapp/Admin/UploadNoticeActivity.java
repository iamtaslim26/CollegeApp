package com.kgec.collegeadminapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kgec.collegeadminapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UploadNoticeActivity extends AppCompatActivity {
    private CardView cardview;
    private EditText editText;
    private Button upload_btn;
    private ImageView show_imageview;
    Uri imageUri;
    private StorageReference Upload_notice_imageRef;
    private DatabaseReference NoticeRef;
    String savecurrentDate,savecurrentTime,PostRefKey;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        loadingbar=new ProgressDialog(this);

        cardview=findViewById(R.id.select_image_cardview);
        editText=findViewById(R.id.notice_title);
        upload_btn=findViewById(R.id.upload_btn);
        show_imageview=findViewById(R.id.upload_notice_view_image);

        NoticeRef= FirebaseDatabase.getInstance().getReference().child("Notice");
        Upload_notice_imageRef= FirebaseStorage.getInstance().getReference().child("Notice");



        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             validatePostInfo();

            }
        });
    }

    private void validatePostInfo() {

        if (TextUtils.isEmpty(editText.getText().toString())){

            Toast.makeText(UploadNoticeActivity.this, "Please Write your title", Toast.LENGTH_SHORT).show();
        }
        else if(imageUri==null) {

            Toast.makeText(UploadNoticeActivity.this, "Please add your picture", Toast.LENGTH_SHORT).show();

        }
        else {

            loadingbar.setMessage("Authenticating.....");
            loadingbar.show();

            uploadImageinFirebaseStorage();

        }
    }

    private void uploadImageinFirebaseStorage() {

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        savecurrentDate=dateFormat.format(calendar.getTime());

        Calendar caltime=Calendar.getInstance();
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh.mm aa");
        savecurrentTime=timeFormat.format(caltime.getTime());

        PostRefKey=savecurrentDate+" "+savecurrentTime;
        StorageReference filepath=Upload_notice_imageRef.child(imageUri.getLastPathSegment()+System.currentTimeMillis()+".jpg");
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                uploadImageinFirebaseDatabase(String.valueOf(uri));
                loadingbar.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UploadNoticeActivity.this, "Failed.. "+e, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        });

    }

    private void uploadImageinFirebaseDatabase(String downloadUrl) {
        
        String uniqueKey=NoticeRef.push().getKey();

        HashMap<String ,Object>map=new HashMap<>();
        map.put("date",savecurrentDate);
        map.put("Time",savecurrentTime);
        map.put("imageUrl",downloadUrl);
        map.put("uid",uniqueKey);
        map.put("title",editText.getText().toString());

        NoticeRef.child(uniqueKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(UploadNoticeActivity.this, "Upload Succesfull. ..  ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    loadingbar.dismiss();
                }
                else {

                    String e=task.getException().getMessage();
                    Toast.makeText(UploadNoticeActivity.this, "Failed....    "+e, Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }

            }
        });


    }

    private void openGallery() {

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==RESULT_OK && data!=null){

            imageUri=data.getData();
            show_imageview.setImageURI(imageUri);

    }
    }
}
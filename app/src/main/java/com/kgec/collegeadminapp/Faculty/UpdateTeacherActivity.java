package com.kgec.collegeadminapp.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kgec.collegeadminapp.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateTeacherActivity extends AppCompatActivity {

    private CircleImageView imageView;
    private EditText et_name,et_email,et_post;
    private Button update_btn,delete_btn;

    private String name,post,image,email,key,category;

    private DatabaseReference reference;
    private StorageReference storageReference;
    private Uri imageUri;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        Initialize();

        name=getIntent().getExtras().get("name").toString();
        email=getIntent().getExtras().get("email").toString();
        post=getIntent().getExtras().get("post").toString();
        image=getIntent().getExtras().get("imageview").toString();
        key=getIntent().getExtras().get("key").toString();
        category=getIntent().getExtras().get("category").toString();

        reference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        storageReference= FirebaseStorage.getInstance().getReference().child("Teachers");

        loadingbar=new ProgressDialog(this);

        ShowDatas();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatePost();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteData();
            }

        });

    }

    private void DeleteData() {

        reference.child(category).child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isComplete()){

                    Toast.makeText(UpdateTeacherActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),UpdateFacultyActivity.class));
                    loadingbar.show();
                }
                else {

                    String e=task.getException().getMessage();
                    Toast.makeText(UpdateTeacherActivity.this, "Failed  "+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void validatePost() {

        if (TextUtils.isEmpty(et_email.getText().toString())){

            Toast.makeText(this, "Please write the email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(et_name.getText().toString())){

            Toast.makeText(this, "Please write the name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(et_post.getText().toString())){

            Toast.makeText(this, "Please write the post...", Toast.LENGTH_SHORT).show();
        }
        else if (imageUri.equals(image)){

            Toast.makeText(this, "Please upload new image", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingbar.setMessage("Authenticating. . .. ");
            loadingbar.show();
            uploadToFirebaseStorage();
        }

    }

    private void uploadToFirebaseStorage() {

        StorageReference filePath=storageReference.child(imageUri.getLastPathSegment()+"-"+System.currentTimeMillis());
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri=uriTask.getResult();

                saveInformationToFirebaseDatabase(String.valueOf(uri));

                loadingbar.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeacherActivity.this, "Failed....   "+e, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        });
    }

    private void saveInformationToFirebaseDatabase(String downloadUrl) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", et_name.getText().toString());
                    map.put("post", et_post.getText().toString());
                    map.put("email", et_email.getText().toString());
                    map.put("imageUrl", downloadUrl);

                    try {
                        reference.child(category).child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isComplete()) {

                                    Toast.makeText(UpdateTeacherActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), UpdateFacultyActivity.class));
                                    loadingbar.dismiss();
                                } else {

                                    String e = task.getException().getMessage();
                                    Toast.makeText(UpdateTeacherActivity.this, "FAiled...  " + e, Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                }

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==RESULT_OK && data!=null){

            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void ShowDatas() {

        et_name.setText(name);
        et_email.setText(email);
        et_post.setText(post);

        try {

            Picasso.get().load(image).into(imageView);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    private void Initialize() {

        imageView=findViewById(R.id.update_profile_image_teacher);
        et_name=findViewById(R.id.update_name_teacher);
        et_email=findViewById(R.id.update_email_teacher);
        et_post=findViewById(R.id.update_post_teacher);
        update_btn=findViewById(R.id.update_upload_btn_teachers);
        delete_btn=findViewById(R.id.delete_btn_teachers);
    }
}
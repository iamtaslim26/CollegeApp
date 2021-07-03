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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeachersActivity extends AppCompatActivity {

    private CircleImageView imageView;
    private EditText name,email,post;
    private Spinner spinner;
    private Button update_btn;
    private Uri imageUri;
    String category;
    private DatabaseReference TeachersRef;
    private StorageReference storageReference;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        Initialize();

        TeachersRef= FirebaseDatabase.getInstance().getReference().child("Teachers");
        storageReference= FirebaseStorage.getInstance().getReference().child("Teachers");
        loadingbar=new ProgressDialog(this);

        String items[]=new String[]{"Select Category","Computer Science","Information Technology",
                                    "Electrical Engineering","Ece dept","Mechanical Dept",
                                        "Physics","Mathematics","Chemistry"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });
    }

    private void checkValidation() {

        if (TextUtils.isEmpty(name.getText().toString())){

            Toast.makeText(this, "Please write the name. . .", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(email.getText().toString())){

            Toast.makeText(this, "Please write the email. . .", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(post.getText().toString())){

            Toast.makeText(this, "Please write the post. . .", Toast.LENGTH_LONG).show();
        }

        else if (category.equals("Select Category")){

            Toast.makeText(this, "Please select the category. . .", Toast.LENGTH_LONG).show();
        }
        else if (imageUri==null){

            Toast.makeText(this, "Please upload image first. . .", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingbar.setMessage("Please Wait, we are Authenticating. .. . ");
            loadingbar.show();

            uploadImageIntoStorage();
        }

    }

    private void uploadImageIntoStorage() {

        StorageReference filePath=storageReference.child(imageUri+"-"+System.currentTimeMillis());

      filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

              Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
              while (!uriTask.isComplete());
              Uri uri=uriTask.getResult();
              uploadDataIntoFirebaseDatabase(String.valueOf(uri));
              loadingbar.dismiss();

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {

              Toast.makeText(AddTeachersActivity.this, "Failed.....   "+e, Toast.LENGTH_SHORT).show();
              loadingbar.dismiss();

          }
      });

    }

    private void uploadDataIntoFirebaseDatabase(String downloadUrl) {

        String id=TeachersRef.push().getKey();

        HashMap<String,Object>map=new HashMap<>();

        map.put("name",name.getText().toString());
        map.put("email",email.getText().toString());
        map.put("post",post.getText().toString());
        map.put("key",id);
        map.put("imageUrl",downloadUrl);

        TeachersRef.child(category).child(id).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(AddTeachersActivity.this, "Data Uploaded. . .", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),UpdateFacultyActivity.class));
                    loadingbar.dismiss();
                }
                else {

                    String e=task.getException().getMessage();
                    Toast.makeText(AddTeachersActivity.this, "Failed....   "+e, Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }

            }
        });


    }

    private void openGallery() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==RESULT_OK && data!=null){

            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void Initialize() {

        imageView=findViewById(R.id.profile_image_teacher);
        name=findViewById(R.id.name_teacher);
        email=findViewById(R.id.email_teacher);
        post=findViewById(R.id.post_teacher);
        update_btn=findViewById(R.id.upload_btn_teachers);
        spinner=findViewById(R.id.teacher_category);
    }
}
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UploadImageActivity extends AppCompatActivity {

    private CardView cardView;
    private EditText editText;
    private Spinner spinner;
    private ImageView show_image;
    private Button upload_btn;
    Uri imageUri;
    String saveCurrentDate,saveCurrentTime,postRefKey;

    private DatabaseReference galleryRef;
    private StorageReference galleryStorageRef;
    private ProgressDialog loadingbar;


    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Initialize();

        String items[]=new String[]{"Select Category","Convocation","Independence day","Other Events"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));


        galleryRef= FirebaseDatabase.getInstance().getReference().child("Gallery");
        galleryStorageRef= FirebaseStorage.getInstance().getReference().child("Gallery");

        loadingbar=new ProgressDialog(this);

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              category=spinner.getSelectedItem().toString();
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              openGallery();
          }
      });


      upload_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              saveUserInfo();
          }
      });
    }

    private void saveUserInfo() {

        if (TextUtils.isEmpty(editText.getText().toString())){

            Toast.makeText(this, "Please write the title first", Toast.LENGTH_SHORT).show();
        }
        else if (imageUri==null){

            Toast.makeText(this, "please upload the image ", Toast.LENGTH_SHORT).show();
        }
        else if (category.equals("Select Category")){

            Toast.makeText(this, "Please select the category.....", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingbar.setMessage("Authenticating. . . . ");
            loadingbar.show();
            saveInfoToFirebaseStorage();
        }
    }

    private void saveInfoToFirebaseStorage() {

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate=dateFormat.format(calForDate.getTime());

        Calendar caltime=Calendar.getInstance();
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm: a");
        saveCurrentTime=timeFormat.format(caltime.getTime());




        StorageReference filepath=galleryStorageRef.child(imageUri.getLastPathSegment()+System.currentTimeMillis()+".jpg");
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                saveInfointoDatabase(String.valueOf(uri));
                loadingbar.dismiss();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UploadImageActivity.this, "Failed.....    "+e, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveInfointoDatabase(String downloadUrl) {

        String uniqueKey=galleryRef.push().getKey();

        HashMap<String,Object>map=new HashMap<>();
        map.put("Date",saveCurrentDate);
        map.put("Time",saveCurrentTime);
        map.put("ImageUrl",downloadUrl);
        map.put("id",uniqueKey);

        galleryRef.child(category).child(uniqueKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(UploadImageActivity.this, "Upload Succesfull....", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    loadingbar.dismiss();
                }
                else {
                    String e=task.getException().getMessage();
                    Toast.makeText(UploadImageActivity.this, "Failed....   "+e, Toast.LENGTH_SHORT).show();
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
            show_image.setImageURI(imageUri);

        }
    }

    private void Initialize() {

        cardView=findViewById(R.id.select_image_cardview_uploadimageactivity);
        editText=findViewById(R.id.image_title);
        spinner=findViewById(R.id.image_category);
        show_image=findViewById(R.id.upload_image_view_image);
        upload_btn=findViewById(R.id.upload_btn_uploadimageactivity);
    }
}
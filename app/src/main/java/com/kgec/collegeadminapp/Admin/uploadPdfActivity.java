package com.kgec.collegeadminapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.File;
import java.util.HashMap;

public class uploadPdfActivity extends AppCompatActivity {

    private CardView cardView;
    private EditText editText;
    private Button upload_btn;
    Uri pdfDataUri;
    private TextView show_pdf_name;
    private String pdfName;
    private DatabaseReference pdfRef;
    private StorageReference pdfStorageRef;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        Initialize();
        loadingbar=new ProgressDialog(this);

        pdfRef= FirebaseDatabase.getInstance().getReference().child("Pdf");
        pdfStorageRef= FirebaseStorage.getInstance().getReference().child("Pdf");

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editText.getText().toString())){

                    Toast.makeText(uploadPdfActivity.this, "Please Write title....", Toast.LENGTH_SHORT).show();

                }
                else if (pdfDataUri==null){

                    Toast.makeText(uploadPdfActivity.this, "Please upload pdf", Toast.LENGTH_SHORT).show();
                }
                else {

                    loadingbar.setMessage("Authenticating.....");
                    loadingbar.show();

                    uploadPdf();

                }
            }
        });
    }

    private void uploadPdf() {

        StorageReference filePath=pdfStorageRef.child(pdfName+"-"+System.currentTimeMillis()+".pdf");

        filePath.putFile(pdfDataUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

                Toast.makeText(uploadPdfActivity.this, "Failed     "+e, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        });

    }

    private void uploadDataIntoFirebaseDatabase(String downloadUrl) {

        String uniqueKey=pdfRef.push().getKey();

        HashMap<String,Object>map=new HashMap<>();
        map.put("pdfTitle",editText.getText().toString());
        map.put("pdfUrl",downloadUrl);

        pdfRef.child(uniqueKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){

                    Toast.makeText(uploadPdfActivity.this, "Data stored...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
                    loadingbar.dismiss();
                    show_pdf_name.setText("");
                }
                else {

                    String e=task.getException().getMessage();
                    Toast.makeText(uploadPdfActivity.this, "Failed...  "+e, Toast.LENGTH_SHORT).show();

                    loadingbar.dismiss();
                }

            }
        });


    }

    private void openGallery() {

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select pdf File"),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==RESULT_OK && data!=null){

            pdfDataUri=data.getData();

            Toast.makeText(this, ""+pdfDataUri, Toast.LENGTH_LONG).show();

            if (pdfDataUri.toString().startsWith("content://")){

                Cursor cursor=null;
                cursor=uploadPdfActivity.this.getContentResolver().query(pdfDataUri,null,null,null,null);
                if (cursor!=null && cursor.moveToFirst()){

                    pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }

            }
            else if(pdfDataUri.toString().startsWith("file://")){

                pdfName=new File(pdfDataUri.toString()).getName();
            }

            show_pdf_name.setText(pdfName);
        }
    }

    private void Initialize() {

        cardView=findViewById(R.id.select_image_cardview_uploadpdfactivity);
        editText=findViewById(R.id.pdf_title);
        upload_btn=findViewById(R.id.upload_btn_uploadpdfactivity);
        show_pdf_name=findViewById(R.id.show_pdf_name);
    }
}
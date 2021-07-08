package com.kgec.collegeadminapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.kgec.collegeadminapp.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PdfViewerActivity extends AppCompatActivity {

    private String get_pdfUrl;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);


        pdfView=findViewById(R.id.pdfView);
        get_pdfUrl=getIntent().getExtras().get("pdfUrl").toString();

        new DownloadPdf().execute(get_pdfUrl);

    }

    private class DownloadPdf extends AsyncTask<String,Void, InputStream>{

        //https://developer.android.com/reference/android/os/AsyncTask

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream=null;

            try {

                URL url=new URL(strings[0]);
                HttpsURLConnection httpsURLConnection= (HttpsURLConnection) url.openConnection();

                if (httpsURLConnection.getResponseCode()==200){

                    inputStream=new BufferedInputStream(httpsURLConnection.getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();


            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            //https://github.com/barteksc/AndroidPdfViewer

            pdfView.fromStream(inputStream).load();
        }
    }


}
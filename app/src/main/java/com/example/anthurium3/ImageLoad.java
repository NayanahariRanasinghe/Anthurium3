package com.example.anthurium3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class ImageLoad extends AppCompatActivity {

    //Timer timer;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        image = findViewById(R.id.imageView2);


        Uri uri = getIntent().getData();
        if(uri != null){
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                //create object on bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //set image
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


//        timer = new Timer();
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent i = new Intent(ImageLoad.this,Detector.class);
//                startActivity(i);
//                finish();
//            }
//        },5000);
    }
}

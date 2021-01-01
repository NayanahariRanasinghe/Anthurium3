package com.example.anthurium3;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.anthurium3.Classifier.ImageClassifier;

public class DiseaseHome extends AppCompatActivity {

    //Requests Codes to identify camera and permission requests
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQEUST_CODE = 10001;

    //UI Elements
    private ImageView imageView;
    private ListView listView;
    ImageClassifier imageClassifier;
    //    private ImageClassifier imageClassifier;
    Button takepicture;
    Button upload;
    Button btnDone;
    String detectorName;
    float detectorConfidence;
    int resultInt = -1;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_home);

        // initalizing ui elements
        initializeUIElements();
    }

    //Method to initalize UI Elements. this method adds the on click
    private void initializeUIElements() {
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.resultList);
        takepicture = findViewById(R.id.btnCamera);
        upload = findViewById(R.id.btnUpload);
        btnDone = findViewById(R.id.btnDone);


        // Creating an instance of our tensor image classifier
        try {
            imageClassifier = new ImageClassifier(this);
        } catch (IOException e) {
            Log.e("Image Classifier Error", "ERROR: " + e);
        }


        // adding on click listener to button
        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking whether camera permissions are available.
                // if permission is avaialble then we open camera intent to get picture
                // otherwise reqeusts for permissions
                if (hasPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i,"Pick an Image"),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // if this is the result of our camera image request
        if (requestCode == CAMERA_REQEUST_CODE) {

            // getting bitmap of the image
            Bitmap photo = (Bitmap) Objects.requireNonNull(Objects.requireNonNull(data).getExtras()).get("data");

            // displaying this bitmap in imageview
            imageView.setImageBitmap(photo);

            //bitmap send for predict
            sendToImageClassifier(photo);

        }
        else if(resultCode == RESULT_OK &&  requestCode == 1) {
            try {
                //call inputStream , get from intent data
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                // Toast.makeText(this, "uri= "+uri, Toast.LENGTH_SHORT).show();

                //create object on bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //set image
                imageView.setImageBitmap(bitmap);

                //bitmap send for predict
                sendToImageClassifier(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendToImageClassifier(Bitmap bitmap) {

        // pass this bitmap to classifier to make prediction
        List<ImageClassifier.Recognition> predicitons = imageClassifier.recognizeImage(
                bitmap, 0);

        // creating a list of string to display in list view
        final List<String> predicitonsList = new ArrayList<>();
        for (ImageClassifier.Recognition recog : predicitons) {
            predicitonsList.add(recog.getName() + "  ::::::::::  " + recog.getConfidence());
            this.detectorName = recog.getName();
            this.detectorConfidence = recog.getConfidence();
        }

        // creating an array adapter to display the classification result in list view
        ArrayAdapter<String> predictionsAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, predicitonsList);
        listView.setAdapter(predictionsAdapter);

//        Toast.makeText(this, "predicitonsList: "+detectorName+" and "+detectorConfidence, Toast.LENGTH_SHORT).show();

        predicDisease(detectorName,detectorConfidence);

    }

    private void predicDisease(String name, float point) {
       Toast.makeText(this, "name: "+name, Toast.LENGTH_SHORT).show();

        if( point >= 0.8){
            switch (name){
                case "1" : this.resultInt = 1;
                    break;
                case "2" : this.resultInt = 2;
                    break;
                case "3" : this.resultInt = 3;
                    break;
                default: this.resultInt = -1;
            }
        }
        else{
            this.resultInt = -1;
            //Toast.makeText(this, "Can not Predict the Result", Toast.LENGTH_SHORT).show();
            alertBox();
        }
           Toast.makeText(this, "resultInt: " + resultInt, Toast.LENGTH_SHORT).show();

    }

    private void alertBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Try again..!!!");
        // Icon Of Alert Dialog
        //alertDialogBuilder.setIcon(R.drawable.question);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Can not predict result...!");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //finish();
            }
        });

//        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(DiseaseHome.this,"You clicked over No",Toast.LENGTH_SHORT).show();
//            }
//        });
//        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public  void getDetection(View v){
        if(v.getId() == R.id.btnDone){
            if(resultInt != -1){
                Intent i = new Intent(DiseaseHome.this,Detector.class);
                i.putExtra("result",resultInt);
                startActivity(i);
            }
            else{
                Toast.makeText(this, "Please upload or take picture...!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // if this is the result of our camera permission request
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (hasAllPermissions(grantResults)) {
                openCamera();
            } else {
                requestPermission();
            }
        }
    }

    /**
     * checks whether all the needed permissions have been granted or not
     *
     * @param grantResults the permission grant results
     * @return true if all the reqested permission has been granted,
     * otherwise returns false
     */
    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    /**
     * Method requests for permission if the android version is marshmallow or above
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // whether permission can be requested or on not
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
            // request the camera permission permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * creates and starts an intent to get a picture from camera
     */
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQEUST_CODE);
    }

    /**
     * checks whether camera permission is available or not
     *
     * @return true if android version is less than marshmallo,
     * otherwise returns whether camera permission has been granted or not
     */
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

}
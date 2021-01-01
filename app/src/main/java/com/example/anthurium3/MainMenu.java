package com.example.anthurium3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    //go to planting page
    public void onClickTest (View v){

        if(v.getId()== R.id.button){
            Intent i = new Intent(MainMenu.this,DiseaseHome.class);
            startActivity(i);
//            finish();
        }
    }

    //go to diseases page
    public void goToDiseases (View v){

        if(v.getId()== R.id.button2){
            Intent i = new Intent(MainMenu.this,DiseasesInfo.class);
            startActivity(i);
//            finish();
        }
    }


    //go to disease detector page
    public void goToDetector(View v){

        if(v.getId() == R.id.btnDetector){
            Intent i = new Intent(MainMenu.this,DiseaseHome.class);
            startActivity(i);
//            finish();
        }
    }

}

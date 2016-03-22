package com.example.david.imasam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.david.imasam.R.id.remoteButton;
import static com.example.david.imasam.R.id.junoButton;
import static com.example.david.imasam.R.id.samButton;
import static com.example.david.imasam.R.id.trexButton;

public class vehicleChooser extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_chooser);

        final View buttonJuno = findViewById(junoButton);
        final View buttonSam = findViewById(samButton);
        final View buttonTrex = findViewById(trexButton);
        final View buttonRemote = findViewById(remoteButton);

        final Intent SamActivity = new Intent(this, ControllerActivity.class);
        final Intent WifiActivity = new Intent(this, WifiActivity.class);

        buttonJuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SamActivity);
            }
        });

        buttonTrex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WifiActivity);
            }
        });
    }







}

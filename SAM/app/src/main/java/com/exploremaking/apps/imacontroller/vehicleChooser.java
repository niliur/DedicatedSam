package com.exploremaking.apps.imacontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.exploremaking.apps.imacontroller.R.id.remoteButton;
import static com.exploremaking.apps.imacontroller.R.id.junoButton;
import static com.exploremaking.apps.imacontroller.R.id.samButton;
import static com.exploremaking.apps.imacontroller.R.id.trexButton;

public class VehicleChooser extends Activity {

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
                SamActivity.putExtra("Vehicle", "JUNO");
                startActivity(SamActivity);
            }
        });

        buttonSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SamActivity.putExtra("Vehicle", "SAM");
                startActivity(SamActivity);
            }
        });

        buttonTrex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SamActivity.putExtra("Vehicle", "TREX");
                startActivity(SamActivity);
            }
        });

        buttonRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WifiActivity);
            }
        });
    }

    @Override
    public void onBackPressed(){
        final Intent blueToothActivity = new Intent(this, BluetoothChooser.class);
        startActivity(blueToothActivity);
        finish();
    }





}

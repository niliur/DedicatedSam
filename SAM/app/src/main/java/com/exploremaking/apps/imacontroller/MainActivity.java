package com.exploremaking.apps.imacontroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import static com.exploremaking.apps.imacontroller.R.id.junoButton;
import static com.exploremaking.apps.imacontroller.R.id.remoteButton;
import static com.exploremaking.apps.imacontroller.R.id.samButton;
import static com.exploremaking.apps.imacontroller.R.id.aboutButton;
import static com.exploremaking.apps.imacontroller.R.id.trexButton;

public class MainActivity extends Activity {

private boolean btchoosen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String website = getString(R.string.website_address);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final int REQUEST_ENABLE_BT = 1;
        final Intent blueToothChoose = new Intent(this, BluetoothChooser.class);
        final Intent aboutPage = new Intent(this, AboutPage.class);
        final Intent WifiActivity = new Intent(this, WifiActivity.class);

        final View buttonAbout = findViewById(aboutButton);        final View buttonJuno = findViewById(junoButton);
        final View buttonSam = findViewById(samButton);
        final View buttonTrex = findViewById(trexButton);
        final View buttonRemote = findViewById(remoteButton);


        buttonJuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueToothChoose.putExtra("mode", "CONTROLLER");
                blueToothChoose.putExtra("Vehicle", "JUNO");
                startActivity(blueToothChoose);
                finish();
            }
        });

        buttonSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueToothChoose.putExtra("mode", "CONTROLLER");
                blueToothChoose.putExtra("Vehicle", "SAM");
                startActivity(blueToothChoose);
                finish();
            }
        });

        buttonTrex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueToothChoose.putExtra("mode", "CONTROLLER");
                blueToothChoose.putExtra("Vehicle", "TREX");
                startActivity(blueToothChoose);
                finish();
            }
        });

        buttonRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueToothChoose.putExtra("mode", "REMOTE");
                startActivity(blueToothChoose);
                finish();
            }
        });


        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device does not support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Enable bluetooth before continuing", Toast.LENGTH_SHORT).show();
        }


        buttonAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(aboutPage);
            }
        });
    }
}

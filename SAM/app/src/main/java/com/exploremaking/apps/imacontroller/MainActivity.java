package com.exploremaking.apps.imacontroller;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.FrameLayout;


import static com.exploremaking.apps.imacontroller.R.id.junoButton;
import static com.exploremaking.apps.imacontroller.R.id.remoteButton;
import static com.exploremaking.apps.imacontroller.R.id.samButton;
import static com.exploremaking.apps.imacontroller.R.id.aboutButton;
import static com.exploremaking.apps.imacontroller.R.id.trexButton;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final int REQUEST_ENABLE_BT = 1;
        final Intent blueToothChoose = new Intent(this, BluetoothChooser.class);
        final Intent aboutPage = new Intent(this, AboutPage.class);

        final View buttonAbout = findViewById(aboutButton);
        final View buttonJuno = findViewById(junoButton);
        final View buttonSam = findViewById(samButton);
        final View buttonTrex = findViewById(trexButton);
        final View buttonRemote = findViewById(remoteButton);
        RelativeLayout.LayoutParams juno = (RelativeLayout.LayoutParams) buttonJuno.getLayoutParams();
        RelativeLayout.LayoutParams sam = (RelativeLayout.LayoutParams) buttonSam.getLayoutParams();
        RelativeLayout.LayoutParams trex = (RelativeLayout.LayoutParams) buttonTrex.getLayoutParams();
        RelativeLayout.LayoutParams remote = (RelativeLayout.LayoutParams) buttonRemote.getLayoutParams();
        RelativeLayout.LayoutParams about = (RelativeLayout.LayoutParams) buttonAbout.getLayoutParams();


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int buttonMargin = 0;
        if (findViewById(R.id.rootRL).getTag().equals("normal")) {
            buttonMargin = (metrics.heightPixels - 5 * (int) getResources().getDimension(R.dimen.main_button_height_normal)) / 8;

        } else if (findViewById(R.id.rootRL).getTag().equals("large")) {
            buttonMargin = (metrics.heightPixels - 5 * (int) getResources().getDimension(R.dimen.main_button_height_large)) / 8;
        }

        //Dynamically setting the margin of the buttons

        juno.topMargin = 2 * buttonMargin;
        buttonJuno.setLayoutParams(juno);

        sam.topMargin = buttonMargin;
        buttonSam.setLayoutParams(sam);

        trex.topMargin = buttonMargin;
        buttonTrex.setLayoutParams(trex);

        remote.topMargin = buttonMargin;
        buttonRemote.setLayoutParams(remote);

        about.topMargin = buttonMargin;
        about.bottomMargin = 2 * buttonMargin;
        buttonAbout.setLayoutParams(about);


        buttonJuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Enable bluetooth before continuing", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothChoose.putExtra("mode", "CONTROLLER");
                    blueToothChoose.putExtra("Vehicle", "JUNO");
                    startActivity(blueToothChoose);
                    finish();
                }
            }
        });

        buttonSam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Enable bluetooth before continuing", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothChoose.putExtra("mode", "CONTROLLER");
                    blueToothChoose.putExtra("Vehicle", "SAM");
                    startActivity(blueToothChoose);
                    finish();
                }
            }
        });

        buttonTrex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Enable bluetooth before continuing", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothChoose.putExtra("mode", "CONTROLLER");
                    blueToothChoose.putExtra("Vehicle", "TREX");
                    startActivity(blueToothChoose);
                    finish();
                }
            }
        });

        buttonRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Enable bluetooth before continuing", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothChoose.putExtra("mode", "REMOTE");
                    startActivity(blueToothChoose);
                    finish();
                }
            }
        });


        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device does not support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(aboutPage);
            }
        });
    }
}

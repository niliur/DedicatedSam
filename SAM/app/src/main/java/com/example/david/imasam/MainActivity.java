package com.example.david.imasam;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import util.CircleSlice;

import static com.example.david.imasam.R.id.startButton;
import static com.example.david.imasam.R.id.aboutButton;

public class MainActivity extends AppCompatActivity {

private boolean btchoosen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String website = getString(R.string.website_address);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final int REQUEST_ENABLE_BT = 1;
        final Intent blueToothChoose = new Intent(this, BluetoothChooser.class);
        final  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));

        Button buttonStart = (Button) findViewById(startButton);
        Button buttonAbout = (Button) findViewById(aboutButton);


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

        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(blueToothChoose);
                finish();
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(browserIntent);
                finish();
            }
        });
    }
}

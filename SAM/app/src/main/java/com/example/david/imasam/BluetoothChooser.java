package com.example.david.imasam;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.Set;

public class BluetoothChooser extends AppCompatActivity {

    private boolean isListopen = false;
    private int REQUEST_ENABLE_BT = 1;
    ArrayAdapter<String> btArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_chooser);
        isListopen = true;

        btArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean found = false;
        final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        new Thread(new Runnable(){
            @Override
            public void run() {
                while(isListopen) {
                    found = mBluetoothAdapter.startDiscovery();




                try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();




    }


    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btArray.add(device.getName() + "\n" + device.getAddress());
                btArray.notifyDataSetChanged();
            }
        }
    };
}
}

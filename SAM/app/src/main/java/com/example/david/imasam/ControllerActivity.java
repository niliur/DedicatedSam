package com.example.david.imasam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import util.LightView;
import util.SeekbarAuto;
import util.VerticalSeekBar;

public class ControllerActivity extends AppCompatActivity {
    private VerticalSeekBar seekbar_left;
    private VerticalSeekBar seekbar_right;
    private LightView lightleft;
    private LightView lightright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contoller);

        final SeekbarAuto seekbarleft = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarLeft));
        final SeekbarAuto seekbarright = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarRight));
        lightleft = (LightView) findViewById(R.id.headlightleft);
        lightright = (LightView) findViewById(R.id.headlightRight);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int leftvalue = seekbarleft.getValue();
                    int rightvalue = seekbarright.getValue();
                    lightleft.colorupdater(leftvalue);
                    lightright.colorupdater(rightvalue);
                    inval(lightleft);
                    inval(lightright);
                    byte[] sent = new byte[2];
                    boolean newsent = false;
                    if (leftvalue == -1){
                        sent[0] = (byte)90;
                    }else{
                        sent[0] = (byte)(leftvalue*2);
                        newsent = true;
                    }

                    if (rightvalue == -1){
                        sent[1] = (byte)90;
                    }else{
                        sent[1] = (byte)(rightvalue*2);
                        newsent = true;
                    }

                    if(BluetoothChooser.valid() && newsent) {
                        BluetoothChooser.write(sent);
                    }

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


/*
        seekbar_left.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        byte[] toByte = new byte[1];
                        toByte[0] = (byte) progress_value;
                        Log.d("myapp", String.valueOf(progress_value));
                        if(BluetoothChooser.valid())
                            BluetoothChooser.write(toByte);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekbar_left.setProgressAndThumb(45);
                    }
                }
        );

        seekbar_right.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress + 91;
                        byte[] toByte = new byte[1];
                        toByte[0] = (byte) progress_value;
                        Log.d("myapp", String.valueOf(progress_value));
                        if(BluetoothChooser.valid())
                            BluetoothChooser.write(toByte);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
*/
    private void inval(final LightView v) {
       this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               v.invalidate();
           }
       });
    }
}

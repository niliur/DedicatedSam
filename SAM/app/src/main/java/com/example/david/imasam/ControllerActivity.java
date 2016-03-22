package com.example.david.imasam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import util.LightViewLeft;
import util.LightViewRight;
import util.SeekbarAuto;
import util.VerticalSeekBar;

public class ControllerActivity extends Activity {
    private LightViewLeft lightleft;
    private LightViewRight lightright;
    private static final int[] STATE_THUMB_TAPPED = {R.attr.state_thumb_tapped};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        final SeekbarAuto seekbarleft = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarLeft));
        final SeekbarAuto seekbarright = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarRight));
        final TextView bluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        //lightleft = (LightViewLeft) findViewById(R.id.lightleft);
        //lightright = (LightViewRight) findViewById(R.id.LightRight);
        final Button optionsButton = (Button) findViewById(R.id.options);
        final Button wifiButton = (Button) findViewById(R.id.wifi);
        final Button bluetoothButton = (Button) findViewById(R.id.bluetooth);
        final View aButton = findViewById(R.id.aButton);
        final View bButton = findViewById(R.id.bButton);
        final View cButton = findViewById(R.id.cButton);
        final Intent blueToothChoose = new Intent(this, BluetoothChooser.class);

        final Intent WifiActivity = new Intent(this, WifiActivity.class);

        wifiButton.setVisibility(View.GONE);
        bluetoothButton.setVisibility(View.GONE);

        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothChooser.write(sent);
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothChooser.write(sent);
            }
        });

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothChooser.write(sent);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiButton.setVisibility(View.VISIBLE);
                bluetoothButton.setVisibility(View.VISIBLE);
                optionsButton.setVisibility(View.GONE);
            }
        });

        bluetoothButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(blueToothChoose);
                finish();
            }
        });

        wifiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(WifiActivity);
                finish();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(BluetoothChooser.valid()) {
                        setText(bluetoothStatus,"Bluetooth connected!");
                        int leftvalue = seekbarleft.getValue();
                        int rightvalue = seekbarright.getValue();
                        //lightleft.colorupdater(leftvalue);
                        //updatelights(lightleft.getShape(), lightleft.colorupdater(leftvalue));
                        //lightright.colorupdater(rightvalue);
                        //updatelights(lightright.getShape(), lightright.colorupdater(rightvalue));
                        //inval(lightleft);
                        //inval(lightright);
                        byte[] sent = new byte[3];
                        boolean newsent = false;
                        sent[0] = (byte) 180;
                        if (leftvalue == -1) {
                            sent[1] = (byte) 90;
                        } else {
                            sent[1] = (byte) (leftvalue * 2);
                            newsent = true;
                        }

                        if (rightvalue == -1) {
                            sent[2] = (byte) 90;
                        } else {
                            sent[2] = (byte) (rightvalue * 2);
                            newsent = true;
                        }

                        if (newsent) {
                            BluetoothChooser.write(sent);
                        }
                    } else
                    setText(bluetoothStatus,"Bluetooth disconnected,\n go to options to reconnect");

                    try {
                        Thread.sleep(100);
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
    private void inval(final LightViewLeft v) {
       this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               v.invalidate();
           }
       });
    }

    private void inval(final LightViewRight v) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.invalidate();
            }
        });
    }

    private void setText(final TextView v, final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setText(s);
            }
        });
    }


    private void updatelights(final GradientDrawable v, final int color) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setColor(color);
            }
        });
    }
}

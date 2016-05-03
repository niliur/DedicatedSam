package com.exploremaking.apps.imacontroller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import util.LightViewLeft;
import util.LightViewRight;
import util.SeekbarAuto;
import util.VerticalSeekBar;

public class ControllerActivity extends Activity {
    private LightViewLeft lightleft;
    private LightViewRight lightright;
    private RelativeLayout layout;
    private boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        layout = (RelativeLayout) findViewById(R.id.controllerBackground);
        setBackground(getIntent().getExtras().getString("Vehicle"));

        isActive = true;

        final SeekbarAuto seekbarleft = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarLeft));
        final SeekbarAuto seekbarright = new SeekbarAuto((VerticalSeekBar) findViewById(R.id.VerticalSeekBarRight));
        final TextView bluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        //lightleft = (LightViewLeft) findViewById(R.id.lightleft);
        //lightright = (LightViewRight) findViewById(R.id.LightRight);
        final View homeButton = findViewById(R.id.home);
        final View aButton = findViewById(R.id.aButton);
        final View bButton = findViewById(R.id.bButton);
        final View cButton = findViewById(R.id.cButton);
        final View buttonHolder = findViewById(R.id.buttonHolder);

        //Setting up proportions
        double backgroundProp = 1500 / 780;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        double displayProp = metrics.widthPixels / metrics.heightPixels;
        boolean displayIsWider = true;
        if (displayProp < backgroundProp) {
            displayIsWider = false;
        }

        int buttonMargin = 0;
        int buttonMarginRight = metrics.widthPixels / 20;
        ;
        RelativeLayout.LayoutParams a = (RelativeLayout.LayoutParams) aButton.getLayoutParams();
        RelativeLayout.LayoutParams b = (RelativeLayout.LayoutParams) bButton.getLayoutParams();
        RelativeLayout.LayoutParams c = (RelativeLayout.LayoutParams) cButton.getLayoutParams();
        FrameLayout.LayoutParams holder = (FrameLayout.LayoutParams) buttonHolder.getLayoutParams();


        if (findViewById(R.id.controllerBackground).getTag().equals("normal")) {
            buttonMargin = (metrics.heightPixels - 3 * (int) getResources().getDimension(R.dimen.controller_button_raduis_normal)) / 6;
        } else if (findViewById(R.id.controllerBackground).getTag().equals("large")) {
            buttonMargin = (metrics.heightPixels - 3 * (int) getResources().getDimension(R.dimen.controller_button_raduis_large)) / 6;
        }


        a.topMargin = 2 * buttonMargin;
        aButton.setLayoutParams(a);

        b.topMargin = buttonMargin;
        bButton.setLayoutParams(b);

        c.topMargin = buttonMargin;
        c.bottomMargin = 2 * buttonMargin;
        cButton.setLayoutParams(c);


        holder.rightMargin = buttonMarginRight;
        buttonHolder.setLayoutParams(holder);


        final Intent homeActivity = new Intent(this, MainActivity.class);


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] sent = new byte[1];

                startActivity(homeActivity);
                BluetoothChooser.cancel();
                finish();
            }
        });


        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] sent = new byte[1];

                sent[0] = (byte) 'w';
                BluetoothChooser.write(sent);
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] sent = new byte[1];
                sent[0] = (byte) ' ';
                BluetoothChooser.write(sent);
            }
        });

        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] sent = new byte[1];
                sent[0] = (byte) 'a';
                BluetoothChooser.write(sent);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive) {
                    if (BluetoothChooser.valid()) {
                        setText(bluetoothStatus, "Bluetooth connected!");
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
                        setText(bluetoothStatus, "Bluetooth disconnected,\n go to options to reconnect");

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

    private void setBackground(String vehicle) {

        Log.d("he", vehicle);
        if (vehicle.equals("JUNO"))
            layout.setBackgroundResource(R.drawable.junocontrollerbackground);
        else if (vehicle.equals("TREX"))
            layout.setBackgroundResource(R.drawable.trexcontrollerbackground);
    }

    @Override
    public void onBackPressed() {
        final Intent bluetoothactivity = new Intent(this, BluetoothChooser.class);
        startActivity(bluetoothactivity);
        BluetoothChooser.cancel();
        isActive = false;
        finish();
    }
}

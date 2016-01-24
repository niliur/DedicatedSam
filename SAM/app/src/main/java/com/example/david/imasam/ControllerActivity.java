package com.example.david.imasam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

public class ControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contoller);

        final VerticalSeekBar seekbar_left = (VerticalSeekBar) findViewById(R.id.VerticalSeekBarLeft);
        final VerticalSeekBar seekbar_right = (VerticalSeekBar) findViewById(R.id.VerticalSeekBarRightt);

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

    }
}

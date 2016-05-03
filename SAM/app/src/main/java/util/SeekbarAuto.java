package util;

import android.widget.SeekBar;

/**
 * Created by David on 2016-01-04.
 */
public class SeekbarAuto{

    private final VerticalSeekBar vseekBar;
    private int value = 0;
    private boolean active;

    public SeekbarAuto(VerticalSeekBar seekBar) {
        this.vseekBar = seekBar;
        active = false;
        this.vseekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        active = true;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        vseekBar.setProgressAndThumb(45);
                        active = false;
                    }
                }
        );
    }

    public int getValue() {
        if (active)
            return value;
        else return -1;
    }
}

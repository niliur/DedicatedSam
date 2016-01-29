package util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by David on 2016-01-28.
 */
public class LightView extends ImageView {

    private Paint paint = new Paint();

    public LightView(Context context) {
        super(context);
        this.superupdater();
    }

    public LightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.superupdater();
    }

    public LightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.superupdater();
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(25, 25, 25, this.paint);

    }


    private void superupdater(){
        this.paint.setColor(Color.WHITE);
        this.paint.setStrokeWidth(20);
        this.paint.setAntiAlias(true);
    }

    public void colorupdater(int value){
        int colorval;
        if (value == -1){
            this.paint.setColor(Color.WHITE);
            return;
        }
        if (value > 45){
            value -= 45;
            this.paint.setColor(Color.rgb(255 - value*5,255,255 - value*5));
        }
        else {
            value = 45 - value;
            this.paint.setColor(Color.rgb(255,255 - value*5,255 - value*5));
        }

    }
}

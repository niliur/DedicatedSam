package util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by David on 2016-01-28.
 */
public class LightViewRight extends ImageView {

    private GradientDrawable bgShape = new GradientDrawable();

    public LightViewRight(Context context) {
        super(context);
    }

    public LightViewRight(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bgShape = (GradientDrawable)this.getBackground();
    }

    public LightViewRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //private RectF shape = new RectF();




    private void superupdater(){
        //this.paint.setColor(Color.WHITE);
        //this.paint.setStrokeWidth(20);
        //this.paint.setAntiAlias(true);
        //Rect temp = new Rect();
        //this.getLocalVisibleRect(temp);
        //shape = new RectF(temp);
    }

    public GradientDrawable getShape(){
        return this.bgShape;
    }

    public int colorupdater(int value){
        int colorval;
        if (value == -1){
            //bgShape.setColor(Color.WHITE);
            return Color.WHITE;
        }
        if (value > 45){
            value -= 45;
            return Color.rgb(255 - value * 5, 255, 255 - value * 5);
        }
        else {
            value = 45 - value;
            return Color.rgb(255, 255 - value * 5, 255 - value * 5);
        }

    }
}

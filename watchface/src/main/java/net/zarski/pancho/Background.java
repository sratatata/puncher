package net.zarski.pancho;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.WindowInsets;

/**
 * Created by lb_lb on 02.12.17.
 */

public class Background implements WatchFaceDrawable {

    private Paint mBackgroundPaint;
    private boolean ambientMode;

    @Override
    public void onCreate(Context context) {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(context.getResources().getColor(R.color.background));
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        // Draw the background.
        if (ambientMode) {
            canvas.drawColor(Color.BLACK);
        } else {
            canvas.drawRect(0, 0, bounds.width(), bounds.height(), mBackgroundPaint);
        }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {

    }

    void setAmbientMode(boolean ambientMode) {
        this.ambientMode = ambientMode;
    }
}

package net.zarski.pancho;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.WindowInsets;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by lb_lb on 02.12.17.
 */

class Clock implements WatchFaceDrawable, WatchFaceUpdatable{
    private Paint mTextPaint;
    private Calendar mCalendar;
    private float mXOffset;
    private float mYOffset;
    private float mXOffsetAmbient;
    private Typeface font;
    private Resources resources;
    /**
     * Whether the display supports fewer bits for each color in ambient mode. When true, we
     * disable anti-aliasing in ambient mode.
     */
    private boolean mLowBitAmbient;
    private boolean mAmbientMode;

    @Override
    public void onCreate(Context context) {
        resources = context.getResources();
        mCalendar = Calendar.getInstance();
        mYOffset = resources.getDimension(R.dimen.digital_y_offset);
        mTextPaint = new Paint();
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pico-8_mono.ttf");
        mTextPaint = createTextPaint(resources.getColor(R.color.digital_text_default));
    }


    private void drawAmbient(Canvas canvas) {
        long now = System.currentTimeMillis();
        mCalendar.setTimeInMillis(now);

        String text = String.format(Locale.US,"%02d:%02d",
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE));
        canvas.drawText(text, mXOffsetAmbient, mYOffset, mTextPaint);
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        if (!mAmbientMode) {
            long now = System.currentTimeMillis();
            mCalendar.setTimeInMillis(now);

            String text = String.format(Locale.US, "%02d:%02d:%02d",
                    mCalendar.get(Calendar.HOUR_OF_DAY),
                    mCalendar.get(Calendar.MINUTE),
                    mCalendar.get(Calendar.SECOND));
            canvas.drawText(text, mXOffset, mYOffset, mTextPaint);
        }else{
            drawAmbient(canvas);
        }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if(visible) {
            mCalendar.setTimeZone(TimeZone.getDefault());
        }
    }

    @Override
    public void update() {
        mCalendar.setTimeZone(TimeZone.getDefault());
    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {
        // Load resources that have alternate values for round watches.
        boolean isRound = insets.isRound();
        if (isRound) {
            mXOffset = resources.getDimension(R.dimen.digital_x_offset_round);
            mXOffsetAmbient = resources.getDimension(R.dimen.digital_x_offset_round_ambient);
        } else {
            mXOffset = resources.getDimension(R.dimen.digital_x_offset);
        }

        float textSize;
        if (isRound) {
            textSize = resources.getDimension(R.dimen.digital_text_size_round);
        } else {
            textSize = resources.getDimension(R.dimen.digital_text_size);
        }

        mTextPaint.setTextSize(textSize);
    }

    void setPaint(int digital_text_punched) {
        mTextPaint.setColor(resources.getColor(digital_text_punched));
    }

    void setAmbientMode(boolean inAmbientMode) {
        if (mLowBitAmbient) {
            mTextPaint.setAntiAlias(!inAmbientMode);
        }
        mAmbientMode = inAmbientMode;
    }

    private Paint createTextPaint(int textColor) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTypeface(font);
        paint.setAntiAlias(true);
        return paint;
    }

    void setLowBitAmbient(boolean lowBitAmbient) {
        this.mLowBitAmbient = lowBitAmbient;
    }
}



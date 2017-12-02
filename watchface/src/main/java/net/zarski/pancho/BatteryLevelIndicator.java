package net.zarski.pancho;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.BatteryManager;
import android.view.WindowInsets;

public class BatteryLevelIndicator implements WatchFaceDrawable{
    private Resources resources;
    private Context context;
    private float mXBattery;
    private float mYBattery;
    private float level = 0.0f;

    public BatteryLevelIndicator(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Context context) {
        resources = context.getResources();
        mXBattery = resources.getDimension(R.dimen.battery_x);
        mYBattery = resources.getDimension(R.dimen.battery_y);
    }

    public void draw(Canvas canvas, Rect bounds){
        level = getBatteryLevel(this.context);
        Paint paint = new Paint();

        if (level < 15f) {
            //green
            paint.setColor(resources.getColor(R.color.digital_text_punched)); //todo remove resources check
        }else {
            //red
            paint.setColor(resources.getColor(R.color.digital_text_default)); //todo remove resources check
        }

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);

        float length = 300f;
        length = length * level;
        canvas.drawLine(mXBattery, mYBattery, mXBattery+length, mYBattery, paint);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {

    }


    private float getBatteryLevel(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return batteryPct;
    }
}

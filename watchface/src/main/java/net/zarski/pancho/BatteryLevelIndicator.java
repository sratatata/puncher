package net.zarski.pancho;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.BatteryManager;

public class BatteryLevelIndicator{
    private Context context;
    private float mXBattery;
    private float mYBattery;

    BatteryLevelIndicator(Context context){
        this.context = context;
    }

    public void onCreate(Resources resources){
        mXBattery = resources.getDimension(R.dimen.battery_x);
        mYBattery = resources.getDimension(R.dimen.battery_y);
    }

    public void draw(Canvas canvas){
        float level = getBatteryLevel();
        Paint paint = new Paint();

        if (level < 15f) {
            //green
            paint.setColor(context.getResources().getColor(R.color.digital_text_1)); //todo remove resources check
        }else {
            //red
            paint.setColor(context.getResources().getColor(R.color.digital_text_2)); //todo remove resources check
        }

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);

        float length = 300f;
        length = length* level;
        canvas.drawLine(mXBattery, mYBattery, mXBattery+length, mYBattery, paint);
    }


    private float getBatteryLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return batteryPct;
    }
}

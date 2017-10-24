package net.zarski.pancho;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class PunchOutListenerService  extends WearableListenerService {

    private static final String TAG = "PunchOutListenerService";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived: " + messageEvent);

        Intent intent = new Intent("sp.app.myWorkClock.Widget.PUNCH_OUT_1x1");
        this.getApplicationContext().sendBroadcast(intent);
    }

}
package net.zarski.pancho;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.WindowInsets;

/**
 * Created by lb_lb on 02.12.17.
 */

interface WatchFaceDrawable {
    void onCreate(Context context);

    void draw(Canvas canvas, Rect bounds);

    void onVisibilityChanged(boolean visible);

    void onApplyWindowInsets(WindowInsets insets);
}

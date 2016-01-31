package com.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * For accessing common methods throughout the app
 * Created by gayathri_nair on 25/03/15.
 */
public class Utils {

    public static float convertDpToPixel(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int getScreenWidthUsingDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

}

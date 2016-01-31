package com.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * For accessing common methods throughout the app
 * Created by gayathri_nair on 25/03/15.
 */
public class Utils {

    private static final String FS_JPG = "fs.jpg";
    private static final String LARGE_JPG = "large.jpg";
    private static final String MEDIUM_JPG = "medium.jpg";
    private static final String NP_MEDIUM_JPG = "_m.jpg";
    private static final String SMALL_JPG = "small.jpg";
    private static final String NP_SMALL_JPG = "_s.jpg";
    protected static Typeface rupeeTypeFace;

    //TODO: Refactoring required, do away with context as a parameter and fetch it using
    // MainApplication.getContext()
    public static float convertDpToPixel(Context context, float dp) {
        Context localContext = context;
        if (null == localContext) {
            localContext = MainApplication.getContext();
        }
        DisplayMetrics displayMetrics = localContext.getResources().getDisplayMetrics();
        return dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelToDp(Context context, float px) {
        Context localContext = context;
        if (null == localContext) {
            localContext = MainApplication.getContext();
        }
        DisplayMetrics displayMetrics = localContext.getResources().getDisplayMetrics();
        return px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertSpToPx(float sp, Context context) {
        Context localContext = context;
        if (null == localContext) {
            localContext = MainApplication.getContext();
        }
        Resources resources = localContext.getResources();
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static void setStringValueToTextView(TextView textView, String value) {
        if (null != textView && null != value) {
            textView.setText(value);
        }
    }

    public static void setSpannableValueToTextView(TextView textView, Spannable value) {
        if (null != textView && null != value) {
            textView.setText(value);
        }
    }

    private static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    private static String getAndroidID(Context context) {
        String androidId = Settings.Secure
                .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId != null) {
            return androidId;
        } else {
            return "";
        }
    }

    public static String getUniqueId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            return getDeviceIMEI(context);
        } else {
            return getAndroidID(context);
        }
    }

    public static boolean checkPermissionGranted(Context appContext, String permission) {
        return ActivityCompat.checkSelfPermission(appContext, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }


    public static String getCurrency() {
        return "` ";
    }

    /**
     * gets screen height in pixels, Application Context should be used
     */
    public static int getScreenHeightUsingDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    /**
     * gets screen width in pixels, Application Context should be used
     */
    public static int getScreenWidthUsingDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static boolean validateEmailPattern(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static double roundTwoDecimals(double d) {
        try {
            DecimalFormat twoDForm = new DecimalFormat("#.#");
            return Double.valueOf(twoDForm.format(d));
        } catch (NumberFormatException nfe) {
            return d;
        }
    }

    public static int getStatusBarHeight(FragmentActivity context) {
        Rect r = new Rect();
        Window w = context.getWindow();
        w.getDecorView().getWindowVisibleDisplayFrame(r);
        return r.top;
    }

    public static int getTitleBarHeight(FragmentActivity context) {
        int viewTop = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return (viewTop - getStatusBarHeight(context));
    }


    public static int getHeightOfRelativeLayout(RelativeLayout layout) {
        return ((RelativeLayout.LayoutParams) layout.getLayoutParams()).height;
    }

    /* a utility to validate Indian phone number example - 03498985532, 5389829422 **/
    public static boolean isValidPhoneNumber(String number) {
        if (!TextUtils.isEmpty(number)) {
            return number.matches("^0?(\\d{10})");
        }
        return false;
    }

    public static boolean isCollectionFilled(Collection<?> collection) {
        return null != collection && collection.isEmpty() == false;
    }

    public static boolean compareLists(List<String> list1, List<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        Collections.sort(list1);
        Collections.sort(list2);
        for (int index = 0; index < list1.size(); index++) {
            if (list1.get(index).equals(list2.get(index)) == false) {
                return false;
            }
        }
        return true;
    }

    public static int getYOnScreen(View v) {

        int[] coordinatesArray = new int[2];
        v.getLocationOnScreen(coordinatesArray);
        return coordinatesArray[1];
    }

    public static int getXOnScreen(View v) {

        int[] coordinatesArray = new int[2];
        v.getLocationOnScreen(coordinatesArray);
        return coordinatesArray[0];
    }


    public static String getManufacturer() {
        String manufacturerInLowerCase = Build.MANUFACTURER.toLowerCase();
        return manufacturerInLowerCase;
    }


    public static String getStringResource(int id) {
        return MainApplication.getContext().getResources().getString(id);
    }

    public static String getStringResource(int id, String substituteText) {
        return MainApplication.getContext().getResources().getString(id, substituteText);
    }

    public static boolean isResourceValid(Integer resId) {
        if (resId != null && (resId >>> 24) < 2) {
            throw new IllegalArgumentException("iconResId not valid");
        } else {
            return true;
        }
    }


    public static boolean hasCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static boolean isDeviceLollipopOrHigher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }


}

package ps.cxl.com.common.util;

import android.util.Log;

/**
 * Created : chenxianglin on 17/4/6.
 */

public class Lg {
    private static boolean SHOW = false;
    private static String TAG = "PsLibrary";

    public static void setDebugMode(boolean show) {
        SHOW = show;
    }

    public static void d(String msg) {
        d("", msg);
    }

    public static void i(String msg) {
        i("", msg);
    }

    public static void w(String msg) {
        w("", msg);
    }

    public static void e(String msg) {
        e("", msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW) {
            Log.d(">>>" + TAG + "<<<" + tag, ">>" + msg);
        }
    }
    public static void i(String tag, String msg) {
        if (SHOW) {
            Log.i(">>>" + TAG + "<<<" + tag, ">>" + msg);
        }
    }
    public static void w(String tag, String msg) {
        if (SHOW) {
            Log.w(">>>" + TAG + "<<<" + tag, ">>" + msg);
        }
    }
    public static void e(String tag, String msg) {
        if (SHOW) {
            Log.e(">>>" + TAG + "<<<" + tag, ">>" + msg);
        }
    }
}

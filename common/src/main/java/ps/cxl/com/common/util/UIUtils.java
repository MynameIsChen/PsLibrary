package ps.cxl.com.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * UI相关工具类
 * Created by wanglx on 16/8/18.
 */
public class UIUtils {

    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;

    /**
     * dp 转 px
     * @param dp dp 值
     * @return px 值
     */
    public static float dpToPx(Context context, float dp){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if(SCREEN_WIDTH == 0){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            SCREEN_WIDTH = displayMetrics.widthPixels;
        }
        return  SCREEN_WIDTH;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if(SCREEN_HEIGHT == 0){
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            SCREEN_HEIGHT = displayMetrics.heightPixels;
        }
        return SCREEN_HEIGHT;
    }
}

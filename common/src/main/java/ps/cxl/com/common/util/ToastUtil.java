package ps.cxl.com.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created : chenxianglin on 17/4/7.
 */

public class ToastUtil {
    private static Context mContext;
    private static Toast mToast;
    private static boolean SHOW = false;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    public static void setDebugMode(boolean show) {
        SHOW = show;
    }

    public static void debug(String m) {
        if (SHOW) {
            toast("调试：" + m);
        }
    }

    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public static void toast(int id) {
        if (mContext != null && id > 0) {
            toast(mContext.getResources().getString(id), Toast.LENGTH_SHORT);
        }
    }

    public static void toast(String msg, int time, Object... o) {
        toast(String.format(msg, o), time);
    }

    public static void toast(String msg, int time) {
        if (SHOW) {
            mToast.setText(msg);
            mToast.setDuration(time);
            mToast.show();
        }
    }
}

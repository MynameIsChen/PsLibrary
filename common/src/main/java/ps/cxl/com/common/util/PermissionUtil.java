package ps.cxl.com.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * 权限管理工具
 */
public class PermissionUtil {

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * activity 请求权限
     * @param act
     * @param requestCode
     * @param permissions
     * @return
     */
    public static boolean buildPermission(Activity act, int requestCode, String... permissions) {
        try {
            boolean hasPermission = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CheckUtil.isEmpty(permissions)) {
                boolean noPermission = false;
                for (String permission : permissions) {
                    noPermission = checkPermission(act, permission);
                }
                if (noPermission) {
                    act.shouldShowRequestPermissionRationale(permissions[0]);
                    act.requestPermissions(new String[]{permissions[0]}, requestCode);
                } else {
                    hasPermission = true;
                }
            } else {
                hasPermission = true;
            }
            return hasPermission;
        } catch (Exception e){
            Lg.e("PermissionUtil", e.getMessage(), e);
            return false;
        }
    }

    /**
     *  fragment 请求权限
     * @param act
     * @param requestCode
     * @param permissions
     * @return
     */
    public static boolean buildPermission(Fragment act, int requestCode, String... permissions) {
        try {
            boolean hasPermission = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !CheckUtil.isEmpty(permissions)) {
                boolean noPermission = false;
                for (String permission : permissions) {
                    noPermission = checkPermission(act.getContext(), permission);
                }
                if (noPermission) {
                    act.shouldShowRequestPermissionRationale(permissions[0]);
                    act.requestPermissions(new String[]{permissions[0]}, requestCode);
                } else {
                    hasPermission = true;
                }
            } else {
                hasPermission = true;
            }
            return hasPermission;
        } catch (Exception e){
            Lg.e("PermissionUtil", e.getMessage(), e);
            return false;
        }
    }
}

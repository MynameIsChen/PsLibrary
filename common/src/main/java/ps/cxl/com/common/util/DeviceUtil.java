package ps.cxl.com.common.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeviceUtil {
    public DeviceUtil() {
    }

    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2;
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Lg.e(e.getMessage());
        }
        return cpuInfo;
    }

    /**
     * 手机唯一标识
     */
    public static String getDeviceId(Context context) {
        try {
            String androidId = "";
            String deviceId = "";
            String serialNum;

            try {
                if (PermissionUtil.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (tm != null) {
                        deviceId = tm.getDeviceId();
                    }
                }
            } catch (Exception e) {
                Lg.e("getDeviceId", e.getMessage(), e);
                e.printStackTrace();
            }

            try {
                androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                Lg.e("getDeviceId", e.getMessage(), e);
                e.printStackTrace();
            }

            serialNum = android.os.Build.SERIAL;
            Lg.d("getDeviceId", androidId + "||" + deviceId + "||" + serialNum + "||");
            return MD5Util.getMD5String(NonNullUtil.get(androidId) + NonNullUtil.get(deviceId) + NonNullUtil.get(serialNum));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMEI(Context context) {
        String deviceId = "";
        try {
            if (PermissionUtil.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    deviceId = tm.getDeviceId();
                }
            }
        } catch (Exception e) {
            Lg.e("getDeviceId", e.getMessage(), e);
            e.printStackTrace();
        }
        return NonNullUtil.get(deviceId);
    }

    public static String getResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
            Lg.e("DeviceUtil", e.getMessage(), e);
            return "0*0";
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo info = context.getApplicationInfo();
            return manager.getPackageInfo(info.packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
            Lg.e("DeviceUtil", e.getMessage(), e);
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo info = context.getApplicationInfo();
            return manager.getPackageInfo(info.packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Lg.e("DeviceUtil", e.getMessage(), e);
            return "UnKnow";
        }
    }

    public static PackageInfo getAppInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            Lg.e("DeviceUtil", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 比较版本号
     *
     * @return 0:相等 1: 旧版本大于新版本号 -1:旧版本小于新版本
     */
    public static int compareVersionNames(String oldVersionName, String newVersionName) {
        if (!VerifyUtil.verifyVersionName(oldVersionName) || !VerifyUtil.verifyVersionName(newVersionName)) {
            return 0;
        }

        int res = 0;
        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);
        try {
            for (int i = 0; i < maxIndex; i++) {
                int oldVersionPart = Integer.valueOf(oldNumbers[i]);
                int newVersionPart = Integer.valueOf(newNumbers[i]);
                if (oldVersionPart < newVersionPart) {
                    res = -1;
                    break;
                } else if (oldVersionPart > newVersionPart) {
                    res = 1;
                    break;
                }
            }
            // If versions are the same so far, but they have different length...
            if (res == 0 && oldNumbers.length != newNumbers.length) {
                res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Lg.e("compareVersionNames", e.getMessage(), e);
        }
        return res;
    }

    /**
     * 是否需要升级
     *
     * @return
     */
    public static boolean needUpgrade(Context context, String newVersionName) {
        return DeviceUtil.compareVersionNames(DeviceUtil.getVersionName(context), newVersionName) == -1;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getApplicationContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            Lg.d(e.getMessage());
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int h = 0;
        int resourceId = context.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            h = context.getResources().getDimensionPixelSize(resourceId);
        }
        return h;
    }

    /**
     * 获取除状态栏的高度
     */
    public static int getUserScreenHeight(Context context) {
        int screenHeight = 0;
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics != null) {
            screenHeight = displayMetrics.heightPixels - getStatusHeight(context);
        }
        return screenHeight;
    }
}

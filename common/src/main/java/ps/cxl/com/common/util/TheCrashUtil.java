package ps.cxl.com.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TheCrashUtil implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private UncaughtExceptionHandler mDefaultHandler;
    private static TheCrashUtil INSTANCE = new TheCrashUtil();
    private Context mContext;
    private Map<String, String> infos = new HashMap();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    private TheCrashUtil() {
    }

    public static TheCrashUtil getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (!this.handleException(ex) && this.mDefaultHandler != null) {
            this.mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                Log.e("CrashHandler", "error : ", e);
            }
            clearStack();
            Process.killProcess(Process.myPid());
            System.exit(1);
        }

    }

    public void clearStack() {
        while (!CheckUtil.isEmpty(runingActivities)) {
            if (runingActivities.iterator() != null && runingActivities.iterator().hasNext()) {
                Activity act = runingActivities.iterator().next();
                act.finish();
                runingActivities.remove(act);
            }
        }
    }

    private Set<Activity> runingActivities;

    public void addToStack(Activity act) {
        if (act != null) {
            if (runingActivities == null) {
                runingActivities = new HashSet<>();
            }
            runingActivities.add(act);
        }
    }

    public void removeFromStack(Activity act) {
        if (act != null) {
            if (!CheckUtil.isEmpty(runingActivities)) {
                if (runingActivities.contains(act)) {
                    runingActivities.remove(act);
                }
            }
        }
    }

    public boolean exitStack(Class act) {
        return !CheckUtil.isEmpty(runingActivities) && act != null && runingActivities.contains(act);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            (new Thread() {
                public void run() {
                    Looper.prepare();
                    Toast.makeText(TheCrashUtil.this.mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();
            this.collectDeviceInfo(this.mContext);
            this.saveCrashInfo2File(ex);
            return true;
        }
    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager fields = ctx.getPackageManager();
            PackageInfo arr$ = fields.getPackageInfo(ctx.getPackageName(), 1);
            if (arr$ != null) {
                String len$ = arr$.versionName == null ? "null" : arr$.versionName;
                String i$ = arr$.versionCode + "";
                this.infos.put("versionName", len$);
                this.infos.put("versionCode", i$);
            }
        } catch (NameNotFoundException var9) {
            Log.e("CrashHandler", "an error occured when collect package info", var9);
        }

        Field[] var10 = Build.class.getDeclaredFields();
        Field[] var11 = var10;
        int var12 = var10.length;

        for (int var13 = 0; var13 < var12; ++var13) {
            Field field = var11[var13];

            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get(null).toString());
                Log.d("CrashHandler", field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e("CrashHandler", "an error occured when collect crash info", e);
            }
        }

    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Iterator writer = this.infos.entrySet().iterator();

        String result;
        while (writer.hasNext()) {
            Entry printWriter = (Entry) writer.next();
            String cause = (String) printWriter.getKey();
            result = (String) printWriter.getValue();
            sb.append(cause + "=" + result + "\n");
        }

        StringWriter writer1 = new StringWriter();
        PrintWriter printWriter1 = new PrintWriter(writer1);
        ex.printStackTrace(printWriter1);

        for (Throwable cause1 = ex.getCause(); cause1 != null; cause1 = cause1.getCause()) {
            cause1.printStackTrace(printWriter1);
        }

        printWriter1.close();
        result = writer1.toString();
        Log.e(TAG, result);
        sb.append(result);

        try {
            long e = System.currentTimeMillis();
            String time = this.formatter.format(new Date());
            String fileName = "crash-" + time + "-" + e + ".txt";
            if (Environment.getExternalStorageState().equals("mounted")) {
                File dir = ExternalFileUtils.getCrashPath();
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(dir.getAbsoluteFile() + File.separator + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception e) {
            Log.e("CrashHandler", "an error occured while writing file...", e);
            return null;
        }
    }

    private void doCopyContent(Context context, String s) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cmb.setText(s);
    }
}

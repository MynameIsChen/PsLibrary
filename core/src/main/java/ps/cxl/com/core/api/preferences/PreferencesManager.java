package ps.cxl.com.core.api.preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {
    public static <T> void putPreferences(Context context, String key, T value , String preferencesName) {
        if(context == null){
            return;
        }
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(value instanceof String){
            editor.putString(key, value.toString());
        }else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if(value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if(value instanceof Long){
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPreferences(Context context, String key, T value, String preferencesName) {
        if(context == null){
            return null;
        }
        Object o = null;
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        if(value instanceof String){
            o =  settings.getString(key, value.toString());
        }else if(value instanceof Boolean){
            o = settings.getBoolean(key, (Boolean) value);
        }else if(value instanceof Integer){
            o = settings.getInt(key, (Integer) value);
        }else if(value instanceof Float){
            o = settings.getFloat(key, (Float) value);
        }else if(value instanceof Long){
            o = settings.getLong(key, (Long) value);
        }
        return (T) o;
    }
}

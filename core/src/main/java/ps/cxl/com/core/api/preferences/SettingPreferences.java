package ps.cxl.com.core.api.preferences;

import android.content.Context;

/**
 * Created : chenxianglin on 16/10/9.
 * 配置相关
 */
public class SettingPreferences {
    private static final String SETTING_PREFERENCES_NAME = "about_setting";//preferences_name

    public static final String INFO_PUSH = "info_push";//消息推送
    public static final String BARRAGE_TOGGLE = "barrage_toggle";//弹幕开关
    public static final String IMG_WIFI = "img_wifi";//仅WiFi网络下载图片
    public static final String SET_ABOUT = "set_about";//关于的内容
    public static final String DEVICE_ID = "device_id";//设备id 唯一标示
    public static final String IMEI = "imei";//imei号
    public static final String NEED_SHOW_HOME_WIZARD = "need_show_home_wizard"; // 需要显示首页向导
    public static final String SHOWED_HOME_WIZARD = "showed_home_wizard"; // 已经显示首页向导
    public static final String SHOWED_RELEASE_NEWS_NEW = "showed_release_news_new"; // 已经显示发布新功能提示

    /**
     * 获取推送配置
     */
    public static boolean getPushSetting(Context context) {
        return PreferencesManager.getPreferences(context, INFO_PUSH, true, SETTING_PREFERENCES_NAME);
    }

    /**
     * 设置推送配置
     */
    public static void putPushSetting(Context context, boolean value){
        PreferencesManager.putPreferences(context, INFO_PUSH, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * 获取弹幕配置
     */
    public static boolean getBarrageSetting(Context context) {
        return PreferencesManager.getPreferences(context, BARRAGE_TOGGLE, true, SETTING_PREFERENCES_NAME);
    }

    /**
     * 设置弹幕配置
     */
    public static void putBarrageSetting(Context context, boolean value){
        PreferencesManager.putPreferences(context.getApplicationContext(), BARRAGE_TOGGLE, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * 获取wifi下图片下载配置
     */
    public static boolean getWifiSetting(Context context) {
        return PreferencesManager.getPreferences(context, IMG_WIFI, false, SETTING_PREFERENCES_NAME);
    }

    /**
     * 设置wifi下图片下载配置
     */
    public static void putWifiSetting(Context context, boolean value){
        PreferencesManager.putPreferences(context, IMG_WIFI, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * 获取关于的内容
     */
    public static String getAboutSetting(Context context) {
        return PreferencesManager.getPreferences(context, SET_ABOUT, "", SETTING_PREFERENCES_NAME);
    }

    /**
     * 设置关于的内容
     */
    public static void putAboutSetting(Context context, String value){
        PreferencesManager.putPreferences(context, SET_ABOUT, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * 手机唯一标识
     */
    public static String getDeviceId(Context context) {
        return PreferencesManager.getPreferences(context, DEVICE_ID, "", SETTING_PREFERENCES_NAME);
    }

    /**
     * 手机唯一标识
     */
    public static void setDeviceId(Context context, String value){
        PreferencesManager.putPreferences(context, DEVICE_ID, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * IMEI
     */
    public static String getIMEI(Context context) {
        return PreferencesManager.getPreferences(context, IMEI, "", SETTING_PREFERENCES_NAME);
    }

    /**
     * IMEI
     */
    public static void setIMEI(Context context, String value) {
        PreferencesManager.putPreferences(context, IMEI, value, SETTING_PREFERENCES_NAME);
    }

    /**
     * 需要显示向导
     */
    public static void setNeedShowHomeWizard(Context context) {
        PreferencesManager.putPreferences(context, NEED_SHOW_HOME_WIZARD, true, SETTING_PREFERENCES_NAME);
    }

    /**
     * 需要显示向导
     */
    public static boolean isNeedShowHomeWizard(Context context) {
        return PreferencesManager.getPreferences(context, NEED_SHOW_HOME_WIZARD, false, SETTING_PREFERENCES_NAME);
    }

    /**
     * 已经显示向导
     */
    public static void setShowedHomeWizard(Context context) {
        PreferencesManager.putPreferences(context, SHOWED_HOME_WIZARD, true, SETTING_PREFERENCES_NAME);
    }

    /**
     * 已经显示向导
     */
    public static boolean isShowedHomeWizard(Context context) {
        return PreferencesManager.getPreferences(context, SHOWED_HOME_WIZARD, false, SETTING_PREFERENCES_NAME);
    }

    /**
     * 已经显示发布新闻新功能提示
     */
    public static void setShowedReleaseNewsNew(Context context) {
        PreferencesManager.putPreferences(context, SHOWED_RELEASE_NEWS_NEW, true, SETTING_PREFERENCES_NAME);
    }

    /**
     * 已经显示发布新闻新功能提示
     */
    public static boolean isShowedReleaseNewsNew(Context context) {
        return PreferencesManager.getPreferences(context, SHOWED_RELEASE_NEWS_NEW, false, SETTING_PREFERENCES_NAME);
    }
}

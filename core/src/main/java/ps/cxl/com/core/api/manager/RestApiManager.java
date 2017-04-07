package ps.cxl.com.core.api.manager;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import ps.cxl.com.common.util.CheckUtil;
import ps.cxl.com.common.util.DeviceUtil;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.R;
import ps.cxl.com.core.api.Constants;
import ps.cxl.com.core.api.Jsonconver.JsonConverterFactory;
import ps.cxl.com.core.api.call.ApiCallAdapterFactory;
import ps.cxl.com.core.api.preferences.SettingPreferences;
import retrofit2.Retrofit;

/**
 * Created : chenxianglin on 17/4/6.
 */

public class RestApiManager {
    private static volatile RestApiManager instance = null;
    private Map<HostSelectionFactory.ApiType, Retrofit> mRetrofitMap = new HashMap<>();
    private List<HostSelectionInterceptor> mHostSelectionInterceptorList = new ArrayList<>();
    private String mStaticParamsHeader;
    private Context mContext;
    private String mCacheDir;
    private boolean mIsDebug = false; //是否打印http报文
    private boolean mTestApi = false; //是否是测试api

    public static RestApiManager getInstance() {
        if (instance == null) {
            synchronized (RestApiManager.class) {
                if (instance == null) {
                    instance = new RestApiManager();
                }
            }
        }
        return instance;
    }

    public RestApiManager init(Context context, String channel) {
        mContext = context.getApplicationContext();
        if (mCacheDir == null) {
            mCacheDir = mContext.getCacheDir() + "/responses";
        }
        if (mStaticParamsHeader == null) {
            mStaticParamsHeader = initStaticParams(mContext, channel);
            Lg.d("X-Static-Params", mStaticParamsHeader);
        }
        return this;
    }

    public RestApiManager setTestApi(boolean isTestApi) {
        mTestApi = isTestApi;
        for (HostSelectionInterceptor interceptor : mHostSelectionInterceptorList) {
            interceptor.setApiEnvironment(isTestApi);
        }
        return this;
    }

    public boolean isTestApi() {
        return mTestApi;
    }

    /**
     * 调试模式
     * 打印http报文 and Stetho
     */
    public RestApiManager debug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    String getStaticParamsHeader() {
        return mStaticParamsHeader;
    }

    public Retrofit getRetrofit(HostSelectionFactory.ApiType type) {
        Retrofit retrofit = mRetrofitMap.get(type);
        if (retrofit != null) {
            return retrofit;
        }
        HostSelectionInterceptor interceptor = HostSelectionFactory.getInterceptor(type);
        interceptor.setApiEnvironment(mTestApi);
        mHostSelectionInterceptorList.add(interceptor);
        retrofit = getRetrofit(interceptor);
        mRetrofitMap.put(type, retrofit);
        return retrofit;
    }

    public Retrofit getDefaultRetrofit() {
        return getRetrofit(HostSelectionFactory.ApiType.DEFAULT);
    }

    private OkHttpClient getOkHttpClient(HostSelectionInterceptor interceptor) {
        return createOkHttpClient(interceptor);
    }

    /**
     * 创建接口
     *
     * @param api api接口
     * @param <T> 实例类型
     * @ 实例
     */
    public <T> T create(final Class<T> api) {
        return getDefaultRetrofit().create(api);
    }

    private OkHttpClient createOkHttpClient(HostSelectionInterceptor interceptor) {
        OkHttpClient okHttpClient;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        Cache cache = null;
        try {
            File httpCacheDirectory = new File(mCacheDir);
            cache = new Cache(httpCacheDirectory, Constants.CACHE_SIZE);
        } catch (Exception e) {
            Lg.e("OKHttp", "Could not create http cache", e);
        }
        if (cache != null) {
            builder.cache(cache);
            builder.addInterceptor(new CacheInterceptor());
        }

        if (interceptor.getOnlineUrl().startsWith("https://")) {
            int[] certificates = {R.raw.ca};
            HttpsManager.SSLParams sslParams = HttpsManager.getSslSocketFactory(certificates);
            if (CheckUtil.isNonEmpty(sslParams)) {
                builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
                String hosts[] = {interceptor.getOnlineUrl(), interceptor.getTestUrl()};
                builder.hostnameVerifier(HttpsManager.getHostnameVerifier(hosts));
            }
        }

        if (mIsDebug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        okHttpClient = builder.build();
        return okHttpClient;
    }

    private Retrofit getRetrofit(HostSelectionInterceptor interceptor) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.callFactory(getOkHttpClient(interceptor));
        builder.baseUrl(interceptor.getOnlineUrl());
        builder.addCallAdapterFactory(ApiCallAdapterFactory.create());
        builder.addConverterFactory(JsonConverterFactory.create());
        return builder.build();
    }

    private String initStaticParams(Context ctx, String channel) {
        String c = channel;
        if (CheckUtil.isEmpty(c)) {
            c = "unknown_channel";
        }
        StringBuilder sb = new StringBuilder();
        String pkgName = ctx.getPackageName();
        sb.append("version=").append(DeviceUtil.getVersionName(ctx));
        sb.append("&").append("code=").append(DeviceUtil.getVersionCode(ctx));
        sb.append("&").append("sys_version=").append(Build.VERSION.RELEASE);
        sb.append("&").append("device=").append(getDevice(Build.MODEL));
        sb.append("&").append("platform=").append("2");//1-ios,2-android
        sb.append("&").append("channelid=").append(c);
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        sb.append("&").append("screen_width=").append(dm.widthPixels);
        sb.append("&").append("screen_height=").append(dm.heightPixels);
        String deviceId = SettingPreferences.getDeviceId(ctx);
        if (CheckUtil.isEmpty(deviceId)) {
            deviceId = DeviceUtil.getDeviceId(ctx);
            sb.append("&").append("deviceId=").append(deviceId);
            SettingPreferences.setDeviceId(ctx, deviceId);
        } else {
            sb.append("&").append("deviceId=").append(deviceId);
        }
        String imei = SettingPreferences.getIMEI(ctx);
        if (CheckUtil.isEmpty(imei)) {
            imei = DeviceUtil.getIMEI(ctx);
            sb.append("&").append("imei=").append(imei);
            SettingPreferences.setIMEI(ctx, imei);
        } else {
            sb.append("&").append("imei=").append(imei);
        }
        return sb.toString();
    }

    /**
     * 过滤汉字
     */
    private String getDevice(String device) {
        try {
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0, length = device.length(); i < length; i++) {
                char c = device.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    stringBuffer.append(String.format("\\u%04x", (int) c));
                } else {
                    stringBuffer.append(c);
                }
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            Lg.e("RestApiManager", e.getMessage(), e);
            return "";
        }
    }
}

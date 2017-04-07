package ps.cxl.com.core.api.manager;

import android.net.Uri;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ps.cxl.com.core.api.Constants;

/**
 * Created : chenxianglin on 17/4/7.
 */

public abstract class HostSelectionInterceptor implements Interceptor {

    protected static final java.lang.String TAG = "HostSelectionInterceptor";
    private volatile boolean mIsTestApi = false;
    private final HostSelectionFactory.ApiType mApiType;

    public HostSelectionInterceptor(HostSelectionFactory.ApiType apiType) {
        mApiType = apiType;
    }

    @Override
    final public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mIsTestApi) {
            request = getTestRequest(request);
        }
        String token = "";
        String header = RestApiManager.getInstance().getStaticParamsHeader();
//        header += ("&uid=" + UserInfoPreferences.getUserId(RestApiManager.getInstance().getContext()));
        request = request.newBuilder()
                .addHeader(Constants.STATIC_PARAMS_HEADER, header)
                .addHeader(Constants.X_AUTH_TOKEN_HEADER, token)
                .build();
        return chain.proceed(request);
    }

    public void setApiEnvironment(boolean isTestApi) {
        mIsTestApi = isTestApi;
    }

    /**
     * 返回测试请求
     */
    private Request getTestRequest(Request request) {
        Uri uri = Uri.parse(getTestUrl());
        HttpUrl.Builder builder = request.url().newBuilder();
        builder.scheme(uri.getScheme()).host(uri.getHost());
        if (uri.getPort() != -1) {
            builder.port(uri.getPort());
        }
        return request.newBuilder().url(builder.build()).build();
    }

    /**
     * 测试服务器
     */
    public abstract String getTestUrl();

    public abstract HostSelectionFactory.ApiType getType();

    public abstract String getOnlineUrl();
}

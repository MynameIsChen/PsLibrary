package ps.cxl.com.core.api.manager;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ps.cxl.com.common.util.NetUtil;
import ps.cxl.com.core.api.Constants;


/**
 * 缓存处理
 * Created by wanglx on 16/4/12.
 */
class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(chain.request().method().equals("GET")){
            if (!NetUtil.isNetworkAvailable(RestApiManager.getInstance().getContext())) {
                if(!request.cacheControl().noCache()){
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + Constants.MAX_STALE)
                            .build();
                }
            }
        }
        return chain.proceed(request);
    }
}
package ps.cxl.com.core.api;

import okhttp3.ResponseBody;
import ps.cxl.com.core.api.call.IApiCall;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created : chenxianglin on 17/4/6.
 */

public interface TestApi {

    @GET("news/detail")
    Call<ResponseBody> getDetail(@Query("news_id") Long id);

    @GET("news/detail")
    IApiCall<Object> getNewsDetail(@Query("news_id") Long id);
}

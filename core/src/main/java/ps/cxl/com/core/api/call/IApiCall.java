package ps.cxl.com.core.api.call;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created : chenxianglin on 17/4/6.
 */

public interface IApiCall<T> {

    interface ErrorType{
        int NETWORK_ERROR = 1;      //网络错误
        int JSON_PARSE_ERROR = 2;   // 数据解析错误
        int SERVER_FAILURE = 3;     // 服务器请求失败
        int NO_DATA__ERROR = 4;     //返回结果为null
        int CLIENT_HTTP_ERROR = 5;  //客户端错误  错误400+
        int SERVER_HTTP_ERROR = 6;  // 服务器http 错误码 500+
    }

    void cancel();

    boolean isCanceled();


    boolean isExecuted();

    void enqueue(IApiCallback<T> callback);

    Response<T> execute() throws IOException;

    IApiCall<T> clone();
}

package ps.cxl.com.core.api.call;

import java.io.IOException;
import java.util.concurrent.Executor;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.common.util.NetUtil;
import ps.cxl.com.core.api.Jsonconver.Hull;
import ps.cxl.com.core.api.Jsonconver.JsonConverterException;
import ps.cxl.com.core.api.Jsonconver.ServerException;
import ps.cxl.com.core.api.manager.RestApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * api请求适配器
 */
public class ApiCallAdapter<T> implements IApiCall<T> {

    private final Call<T> call;
    private final Executor callbackExecutor;

    public ApiCallAdapter(Executor callbackExecutor, Call<T> call) {
        this.callbackExecutor = callbackExecutor;
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void enqueue(final IApiCallback<T> callback) {
        callback.before();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                if(call.isCanceled()){
                    return;
                }
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        int code = response.code();
                        Hull<T> hull  = (Hull<T>) response.body();
                        if (code >= 200 && code < 300) {
                            if((hull.data != null)){
                                callback.success(response, hull.data);
                            }else {
                                callback.error(ErrorType.NO_DATA__ERROR, 0, "data is null");
                            }
                        } else if (code >= 400 && code < 500) {
                            callback.error(ErrorType.CLIENT_HTTP_ERROR, code, "http error code: " + code);
                        } else {
                            if (NetUtil.isNetworkAvailable(RestApiManager.getInstance().getContext())) {
                                if (hull == null) {
                                    callback.error(ErrorType.SERVER_HTTP_ERROR, code, "http error code: " + code);
                                    Lg.e("REQUEST HTTP ERROR");
                                } else {
                                    callback.error(ErrorType.SERVER_HTTP_ERROR, code, hull.msg);
                                    Lg.e("REQUEST JSON ERROR", hull.msg);
                                }
                            } else {
                                callback.error(ErrorType.NETWORK_ERROR, 0, "");
                            }
                        }
                        callback.finish();
                    }
                });
            }
            @Override
            public void onFailure(Call<T> call, final Throwable t) {
                if(call.isCanceled()){
                    return;
                }
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(t instanceof JsonConverterException){
                            callback.error(ErrorType.JSON_PARSE_ERROR, 0, t.getMessage());
                            Lg.e("REQUEST JSON ERROR", t.getMessage() ,t);
                        } else if(t instanceof ServerException) {
                            ServerException exception = (ServerException) t;
                            callback.error(ErrorType.SERVER_FAILURE, exception.getCode(), t.getMessage());
                            Lg.e("REQUEST SERVER ERROR", t.getMessage() ,t);
                        }else {
                            callback.error(ErrorType.NETWORK_ERROR, 0, t.getMessage());
                            Lg.e("REQUEST NETWORK ERROR", t.getMessage() ,t);
                        }
                        callback.finish();
                    }
                });
            }
        });
    }

    @Override
    public Response<T> execute() throws IOException {
        return call.execute();
    }

    @Override
    public IApiCall<T> clone() {
        return new ApiCallAdapter<>(callbackExecutor, call.clone());
    }
}

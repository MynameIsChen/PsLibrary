package ps.cxl.com.core.api.call;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * api请求接口工厂
 * Created by wanglx on 16/7/27.
 */
public class ApiCallAdapterFactory extends CallAdapter.Factory{

    public static ApiCallAdapterFactory create(){
        return new ApiCallAdapterFactory();
    }
    @Override
    public CallAdapter<IApiCall<?>> get(Type returnType, Annotation[] annotations,
                                        Retrofit retrofit) {
        if (getRawType(returnType) != IApiCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("ApiCall must have generic type (e.g., ApiCall<ResponseBody>)");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        final Executor callbackExecutor = retrofit.callbackExecutor();
        return new CallAdapter<IApiCall<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public <R> IApiCall<R> adapt(Call<R> call) {
                return new ApiCallAdapter<>(callbackExecutor, call);
            }
        };
    }
}

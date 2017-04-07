package ps.cxl.com.core.api.call;

import ps.cxl.com.common.util.CheckUtil;
import ps.cxl.com.common.util.ToastUtil;
import retrofit2.Response;

/**
 * Created : chenxianglin on 17/4/6.
 */

public abstract class IApiCallback<T> {

    public void before() {

    }

    public abstract void success(Response<T> response, T result) ;

    public void error(int errorType, int code, String message){
        if (IApiCall.ErrorType.NETWORK_ERROR == errorType || IApiCall.ErrorType.CLIENT_HTTP_ERROR == errorType) {
            ToastUtil.toast("加载失败，请检查网络！");
        } else if (IApiCall.ErrorType.SERVER_HTTP_ERROR == errorType) {
            if (code == 500) {
                ToastUtil.toast("服务器繁忙，请稍后！");
            }
        } else if (IApiCall.ErrorType.SERVER_FAILURE == errorType) {
            if (CheckUtil.isNonEmpty(message)) {
                ToastUtil.toast(message);
            }
        }
    }

    public void finish(){

    }
}

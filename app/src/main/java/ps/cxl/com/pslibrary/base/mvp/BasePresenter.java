package ps.cxl.com.pslibrary.base.mvp;

import ps.cxl.com.core.api.manager.RestApiManager;

/**
 * Created : chenxianglin on 17/4/11.
 */

public class BasePresenter implements IPresenter {
    protected String TAG;
    public BasePresenter() {
        TAG = getClass().getSimpleName();
    }

    /**
     * 创建api
     */
    protected  <T> T createApi(final Class<T> api) {
        return RestApiManager.getInstance().create(api);
    }

    public boolean isLogin(){
       return true;
    }

    @Override
    public void start() {

    }


    public void destroy(){

    }
}

package ps.cxl.com.pslibrary;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.api.manager.RestApiManager;
import ps.cxl.com.pslibrary.base.BaseApplication;

/**
 * Created : chenxianglin on 17/4/6.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        RestApiManager.getInstance().init(this, "default");
        Lg.setDebugMode(BuildConfig.DEBUG);
    }
}

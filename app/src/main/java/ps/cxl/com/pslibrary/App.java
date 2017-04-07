package ps.cxl.com.pslibrary;

import android.app.Application;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.api.manager.RestApiManager;

/**
 * Created : chenxianglin on 17/4/6.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        RestApiManager.getInstance().init(this,"default");
        Lg.setDebugMode(BuildConfig.DEBUG);
    }
}

package ps.cxl.com.pslibrary.splash;

import ps.cxl.com.pslibrary.base.mvp.BasePresenter;
import ps.cxl.com.pslibrary.base.mvp.BaseView;
import ps.cxl.com.pslibrary.base.mvp.IContract;

public interface SplashContract extends IContract {
    interface View extends BaseView<Presenter> {

    }

    abstract class Presenter extends BasePresenter {

    }
}

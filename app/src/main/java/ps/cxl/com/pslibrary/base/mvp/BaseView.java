package ps.cxl.com.pslibrary.base.mvp;

/**
 * Created : chenxianglin on 17/4/11.
 */

public interface BaseView<T extends IPresenter> extends IView<T> {
    @Override
    void setPresenter(T presenter);
}

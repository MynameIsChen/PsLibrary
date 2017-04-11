package ps.cxl.com.pslibrary.base.mvp;

/**
 * Created : chenxianglin on 17/4/11.
 */

public interface IView<T extends IPresenter> {
    void setPresenter(T presenter);
}

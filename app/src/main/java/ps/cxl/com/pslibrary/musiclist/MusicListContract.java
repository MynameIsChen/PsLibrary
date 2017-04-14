package ps.cxl.com.pslibrary.musiclist;

import android.content.ContentResolver;

import ps.cxl.com.core.bean.MusicListEntity;
import ps.cxl.com.pslibrary.base.mvp.BasePresenter;
import ps.cxl.com.pslibrary.base.mvp.BaseView;
import ps.cxl.com.pslibrary.base.mvp.IContract;

public interface MusicListContract extends IContract {
    interface View extends BaseView<Presenter> {
        void setMusicList(MusicListEntity entity);
    }

    abstract class Presenter extends BasePresenter {
        abstract MusicListEntity findMusicList(ContentResolver resolver);
    }
}

package ps.cxl.com.pslibrary.musicinfo;

public class MusicInfoPresenter extends MusicInfoContract.Presenter {
    private MusicInfoContract.View mView;

    private MusicInfoPresenter(MusicInfoContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public static MusicInfoPresenter create(MusicInfoContract.View view) {
        return new MusicInfoPresenter(view);
    }
}

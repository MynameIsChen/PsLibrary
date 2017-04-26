package ps.cxl.com.pslibrary.preview;

public class PreviewPresenter extends PreviewContract.Presenter {
    private PreviewContract.View mView;

    private PreviewPresenter(PreviewContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public static PreviewPresenter create(PreviewContract.View view) {
        return new PreviewPresenter(view);
    }
}

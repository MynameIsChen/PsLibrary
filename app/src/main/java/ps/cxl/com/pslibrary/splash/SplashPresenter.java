package ps.cxl.com.pslibrary.splash;

public class SplashPresenter extends SplashContract.Presenter {
    private SplashContract.View mView;

    private SplashPresenter(SplashContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public static SplashPresenter create(SplashContract.View view) {
        return new SplashPresenter(view);
    }

}

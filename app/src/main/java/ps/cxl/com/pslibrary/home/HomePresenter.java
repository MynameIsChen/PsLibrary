package ps.cxl.com.pslibrary.home;

public class HomePresenter extends HomeContract.Presenter {
    private HomeContract.View mView;

    private HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    public static HomePresenter create(HomeContract.View view) {
        return new HomePresenter(view);
    }
}

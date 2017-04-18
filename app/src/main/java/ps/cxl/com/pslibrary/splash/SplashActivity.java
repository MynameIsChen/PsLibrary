package ps.cxl.com.pslibrary.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.airbnb.lottie.LottieAnimationView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;
import ps.cxl.com.pslibrary.home.HomeActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @BindView(R.id.animation)
    LottieAnimationView mAnimation;
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        SplashPresenter.create(this);
        new LaunchHandler(this).sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private static class LaunchHandler extends Handler {
        private WeakReference<Context> mContext;

        LaunchHandler(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Context context = mContext.get();
            if (context != null) {
                HomeActivity.lanuch(context);
            }
        }
    }
}

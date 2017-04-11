package ps.cxl.com.pslibrary.home;

import android.os.Bundle;

import butterknife.ButterKnife;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    private final String TAG = "HomeActivity";
    private HomeFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mFragment = (HomeFragment) getFragment("HomeFragment");
    }

    @Override
    public void onBackPressed() {
        mFragment.toast();
        super.onBackPressed();
    }
}

package ps.cxl.com.pslibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ps.cxl.com.common.util.TheCrashUtil;
import ps.cxl.com.pslibrary.R;

/**
 * Created : chenxianglin on 17/4/5.
 */

public class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    private Fragment mCurrentFragment;

    protected void replaceFragment(String tag, Fragment fragment) {
        replaceFragment(tag, R.id.fragment, fragment);
    }

    protected void replaceFragment(String tag, int id, Fragment fragment) {
        if (mCurrentFragment != null) {
            mCurrentFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(id, fragment, tag);
            transaction.commit();
        }
    }

    protected Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TheCrashUtil.getInstance().addToStack(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TheCrashUtil.getInstance().removeFromStack(this);
    }
}

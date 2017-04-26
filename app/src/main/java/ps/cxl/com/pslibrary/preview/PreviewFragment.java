package ps.cxl.com.pslibrary.preview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ps.cxl.com.common.util.UIUtils;
import ps.cxl.com.pslibrary.App;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseFragment;

public class PreviewFragment extends BaseFragment implements PreviewContract.View {
    @BindView(R.id.pager)
    ViewPager mPager;
    Unbinder unbinder;
    private PreviewContract.Presenter mPresenter;
    private View mRoot;
    private  PreviewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreviewPresenter.create(this);
    }

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_preview, null);
        }
        unbinder = ButterKnife.bind(this, mRoot);
        init();
        return mRoot;
    }

    @Override
    public void setPresenter(PreviewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void init(){
        //建议把RelativeLayout与ViewPager抽离出来做一个view管理
        //http://blog.csdn.net/u011494050/article/details/41183061
        //配置OnTouchListener， dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
        mAdapter = new PreviewAdapter();
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);
        mPager.setPageTransformer(true,new PagerTransformation());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPager.getLayoutParams();
        params.topMargin = (int) UIUtils.dpToPx(mPager.getContext(), 25);
        params.leftMargin = (int) UIUtils.dpToPx(mPager.getContext(), 50);
        params.rightMargin = (int) UIUtils.dpToPx(mPager.getContext(), 50);
//        mPager.setPadding(100,100,100,100);
        params.height = (int) UIUtils.dpToPx(mPager.getContext(),100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

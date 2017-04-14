package ps.cxl.com.pslibrary.musicinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseFragment;

public class MusicInfoFragment extends BaseFragment implements MusicInfoContract.View {
    private MusicInfoContract.Presenter mPresenter;
    private View mRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicInfoPresenter.create(this);
    }

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_music_info, null);
        }
        return mRoot;
    }

    @Override
    public void setPresenter(MusicInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

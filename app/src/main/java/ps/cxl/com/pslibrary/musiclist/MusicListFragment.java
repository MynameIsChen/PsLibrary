package ps.cxl.com.pslibrary.musiclist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ps.cxl.com.common.util.ToastUtil;
import ps.cxl.com.core.bean.MusicListEntity;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseFragment;
import ps.cxl.com.pslibrary.base.adapter.BaseDataAdapter;
import ps.cxl.com.pslibrary.base.adapter.OnItemClickedListener;
import ps.cxl.com.pslibrary.musicinfo.MusicInfoActivity;
import ps.cxl.com.pslibrary.widgets.loading.LoadingDataBaseLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListFragment extends BaseFragment implements MusicListContract.View {
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.loading_layout)
    LoadingDataBaseLayout mLoadingLayout;
    private MusicListContract.Presenter mPresenter;
    private View mRoot;
    private MusicListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicListPresenter.create(this);
    }

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_music_list, null);
            ButterKnife.bind(this, mRoot);
        }
        initView();
        return mRoot;
    }

    private void initView() {
        mAdapter = new MusicListAdapter(getContext());
        mList.setAdapter(mAdapter);
        mPresenter.findMusicList(getContext().getContentResolver());
        mAdapter.setListener(new OnItemClickedListener() {
            @Override
            public void onItemClick(int viewType, int position, long arg0, int arg1, String arg2) {

            }

            @Override
            public void onItemClick(int action, int arg, BaseDataAdapter.ViewHolder holder) {
                if (action == MusicListAdapter.MUSIC_ITEM) {
                    MusicInfoActivity.launch(getContext());
                } else if (action == MusicListAdapter.MUSIC_MORE) {
                    ToastUtil.toast("position=" + arg);
                }
            }
        });
    }

    @Override
    public void setPresenter(MusicListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setMusicList(MusicListEntity entity) {
        mAdapter.setData(entity.getList());
    }
}

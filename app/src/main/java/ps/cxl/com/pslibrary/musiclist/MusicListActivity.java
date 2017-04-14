package ps.cxl.com.pslibrary.musiclist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;

import ps.cxl.com.pslibrary.widgets.baseview.TitleView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleView mTitle;

    private MusicListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFragment = (MusicListFragment) getFragment("MusicListFragment");
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MusicListActivity.class));
    }
}

package ps.cxl.com.pslibrary.musicinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;
import ps.cxl.com.pslibrary.widgets.baseview.TitleView;

public class MusicInfoActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleView mTitle;
    private MusicInfoFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setTitleText("info");
        mTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFragment = (MusicInfoFragment) getFragment("mFragment");
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MusicInfoActivity.class));
    }
}

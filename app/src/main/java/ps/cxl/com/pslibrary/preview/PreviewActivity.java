package ps.cxl.com.pslibrary.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;
import ps.cxl.com.pslibrary.widgets.baseview.TitleView;

public class PreviewActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleView mTitle;
    private PreviewFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mFragment = (PreviewFragment) getFragment("PreviewFragment");
        mTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void lanuch(Context context){
        context.startActivity(new Intent(context,PreviewActivity.class));
    }
}

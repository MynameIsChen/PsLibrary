package ps.cxl.com.pslibrary.widgets.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import ps.cxl.com.pslibrary.R;


/**
 * loading data base layout
 * Created by wanglx on 16/1/5.
 */
public class LoadingDataBaseLayout extends RelativeLayout implements OnClickListener {
    private ReloadDataListener mReloadDataListener;
    private View mLoading;
    private View mError;
    private View mEmpty;
    private Animation mLoadingAnim;
    private View mIcon;

    public LoadingDataBaseLayout(Context context) {
        super(context);
        initLayout();
    }

    public LoadingDataBaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public LoadingDataBaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    public void setReloadDataListener(ReloadDataListener listener){
        mReloadDataListener = listener;
    }

    private void initLayout(){
        mLoading  = getLoadingLayout();
        mError = getErrorLayout();
        mEmpty = getEmptyLayout();
        mError.setOnClickListener(this);
        addView(mLoading, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mEmpty, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mError, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void loading(){
        mError.setVisibility(GONE);
        mEmpty.setVisibility(GONE);
        mLoading.setVisibility(VISIBLE);
        mIcon.setAnimation(mLoadingAnim);
        mLoadingAnim.start();
        mLoading.bringToFront();
    }

    public void error(){
        mError.setVisibility(VISIBLE);
        mLoading.setVisibility(GONE);
        mLoadingAnim.cancel();
        mEmpty.setVisibility(GONE);
        mError.bringToFront();
    }

    public void empty(){
        mEmpty.setVisibility(VISIBLE);
        mLoading.setVisibility(GONE);
        mLoadingAnim.cancel();
        mError.setVisibility(GONE);
        mEmpty.bringToFront();
    }

    public void finish(){
        mError.setVisibility(GONE);
        mLoading.setVisibility(GONE);
        mLoadingAnim.cancel();
        mEmpty.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if(mReloadDataListener != null){
            loading();
            mReloadDataListener.reloadData();
        }
    }

    private Animation createLoadingAnim(){
        Animation rotateAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        return rotateAnimation;
    }

    /**
     * 加载页 view
     */
    public View getLoadingLayout(){
        View loading = inflate(getContext(), R.layout.load_loading_layout, null);
        mIcon = loading.findViewById(R.id.load_loading_icon);
        mLoadingAnim = createLoadingAnim();
        return loading;
    }

    /**
     * 错误页view
     */
    public View getErrorLayout(){
        return inflate(getContext(), R.layout.load_error_layout, null);
    }

    /**
     * 空数据 view
     */
    public View getEmptyLayout(){
        return inflate(getContext(), R.layout.load_empty_layout, null);
    }

    public interface ReloadDataListener{
        void reloadData();
    }
}

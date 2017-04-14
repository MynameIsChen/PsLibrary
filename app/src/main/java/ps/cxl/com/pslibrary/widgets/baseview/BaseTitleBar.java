package ps.cxl.com.pslibrary.widgets.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ps.cxl.com.common.util.UIUtils;
import ps.cxl.com.pslibrary.R;


/**
 * Created : chenjianglin on 16/8/3.
 * 仅带返回按钮的顶部控件
 */
public class BaseTitleBar extends RelativeLayout {
    protected ImageView mBack;
    private View line;

    public BaseTitleBar(Context context) {
        super(context);
        init(context);
    }

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        if(context != null) {
            float backWidth = UIUtils.dpToPx(context, 45);
            float lineHeight = UIUtils.dpToPx(context, 0.5f);
            mBack = new ImageView(context);
            LayoutParams backParams = new LayoutParams((int)backWidth, LayoutParams.MATCH_PARENT);
            mBack.setImageResource(R.drawable.item_icon_back);
            mBack.setScaleType(ImageView.ScaleType.CENTER);
            this.addView(mBack,backParams);

            line = new View(context);
            LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT,(int)lineHeight);
            lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            line.setBackgroundColor(context.getResources().getColor(R.color.color_d6d6d6));
            line.setVisibility(View.VISIBLE);
            this.addView(line,lineParams);
        }
    }

    public void setOnBackListener(OnClickListener listener){
        if(mBack != null && listener != null) {
            mBack.setOnClickListener(listener);
        }
    }

    public ImageView getBack() {
        return mBack;
    }

    public void setLineVisible(boolean visible){
        line.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setBackImage(int id){
        mBack.setImageResource(id);
    }

    public void setSelectBack(boolean select){
        mBack.setSelected(select);
    }
}

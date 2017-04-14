package ps.cxl.com.pslibrary.widgets.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ps.cxl.com.common.util.UIUtils;
import ps.cxl.com.pslibrary.R;


/**
 * Created : chenjianglin on 16/8/4.
 * 附加title的顶部控件
 */
public class TitleView extends BaseTitleBar {
    protected TextView mTitle;

    public TitleView(Context context) {
        super(context);
        init(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        addTitle(context, attrs);
    }

    private void addTitle(Context context, AttributeSet attrs) {
        float textSize = UIUtils.dpToPx(context, 17);
        String text = "";
        if (attrs != null) {
            TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
            if (type != null) {
                text = type.getString(R.styleable.TitleView_backText);
                type.recycle();
            }
        }
        mTitle = new TextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = (int) UIUtils.dpToPx(context, 45);
        layoutParams.rightMargin = (int) UIUtils.dpToPx(context, 45);
        mTitle.setText(TextUtils.isEmpty(text) ? "" : text);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTitle.setSingleLine(true);
        mTitle.setTextColor(context.getResources().getColor(R.color.color_333333));
        mTitle.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(mTitle, layoutParams);
    }

    public void setOnTitleListener(View.OnClickListener listener) {
        if (mTitle != null && listener != null) {
            mTitle.setOnClickListener(listener);
        }
    }

    public void setTitleText(String title) {
        mTitle.setText(title);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public void setTitleColor(int color) {
        if (mTitle != null) {
            mTitle.setTextColor(getResources().getColor(color));
        }
    }

    public void setSelectTitle(boolean select){
        if(select) {
           setTitleColor(R.color.white);
        }else{
            setTitleColor(R.color.color_333333);
        }
    }
}

package ps.cxl.com.pslibrary.widgets.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ps.cxl.com.pslibrary.R;


/**
 * 简单button
 * Created by wanglx on 16/8/23.
 */
public class ButtonTitleView extends TitleView {

    private TextView mButton;

    public ButtonTitleView(Context context) {
        super(context);
        init(context);
    }

    public ButtonTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ButtonTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view =  inflate(context, R.layout.shortcut_edit_title_button, this);
        LinearLayout buttons = (LinearLayout) view.findViewById(R.id.buttons);
        mButton = (TextView) view.findViewById(R.id.edit);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        buttons.setGravity(Gravity.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layoutParams.addRule(RelativeLayout.RIGHT_OF, mTitle.getId());
        buttons.setLayoutParams(layoutParams);
    }

    /**
     * 编辑按钮点击监听
     * @param listener
     */
    public void setButtonClickListener(View.OnClickListener listener){
        mButton.setOnClickListener(listener);
    }

    public void setButtonText(String text){
        mButton.setText(text);
    }

    public void setButtonText(int resId){
        mButton.setText(resId);
    }
}

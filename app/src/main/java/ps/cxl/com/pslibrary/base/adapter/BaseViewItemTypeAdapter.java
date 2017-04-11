package ps.cxl.com.pslibrary.base.adapter;

/**
 * view项类型适配器 基础类
 * Created by wanglx on 16/8/22.
 */
public abstract class BaseViewItemTypeAdapter<T> implements IViewItemTypeAdapter<T> {

    protected OnItemClickedListener mListener;

    public BaseViewItemTypeAdapter(OnItemClickedListener listener) {
        mListener = listener;
    }
}

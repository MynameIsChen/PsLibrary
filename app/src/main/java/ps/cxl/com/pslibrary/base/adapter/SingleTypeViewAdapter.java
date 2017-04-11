package ps.cxl.com.pslibrary.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import ps.cxl.com.core.bean.BaseEntity;

/**
 * 单一类型view 适配器
 * Created by wanglx on 16/8/4.
 */

@SuppressWarnings("unused")
public abstract class SingleTypeViewAdapter<T extends BaseEntity> extends BaseDataAdapter<T> {

    protected OnItemClickedListener mOnItemClickedListener;

    public SingleTypeViewAdapter(List<T> list, Context context) {
        super(list, context);
    }

    public SingleTypeViewAdapter(Context context) {
        super(context);
    }

    public void setListener(OnItemClickedListener listener){
        mOnItemClickedListener = listener;
    }

    @Override
    public final View createView(int position, int viewType, ViewGroup parent, LayoutInflater inflater) {
        return createView(parent, inflater);
    }

    @Override
    public final ViewHolder getViewHolder(int viewType, View view) {
        return getViewHolder(view);
    }

    @Override
    public final void onBindData(ViewHolder holder, int viewType) {
        onBindData(holder);
    }

    @Override
    public final int getViewTypeCount() {
        return 1;
    }

    @Override
    public final int getItemViewType(int position) {
        return 0;
    }

    /**
     * 创建view
     * @param parent  父view
     * @param inflater 加载器
     */
    public abstract View createView(ViewGroup parent, LayoutInflater inflater);

    /**
     * 获取 viewholder
     */
    public abstract ViewHolder getViewHolder(View view);

    /**
     * 绑定数据到 view holder
     * @param holder  view holder
     */
    public abstract void onBindData(ViewHolder holder);
}

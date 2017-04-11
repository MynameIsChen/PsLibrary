package ps.cxl.com.pslibrary.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import ps.cxl.com.core.bean.BaseEntity;

/**
 * 多view类型适配器
 * Created by wanglx on 16/8/4.
 */
@SuppressWarnings("unused")
public abstract class  MultiTypeViewAdapter<T extends BaseEntity> extends BaseDataAdapter<T> {
    private SparseArray<IViewItemTypeAdapter> mTypeAdapters = new SparseArray<>();

    public MultiTypeViewAdapter(List<T> list, Context context) {
        super(list, context);
    }

    public MultiTypeViewAdapter(Context context) {
        super(context);
    }

    public void setTypeAdapters(SparseArray<IViewItemTypeAdapter> typeAdapters) {
        mTypeAdapters = typeAdapters;
    }

    public void addTypeAdapter(IViewItemTypeAdapter adapter) {
        mTypeAdapters.put(adapter.getType(), adapter);
    }

    @Override
    final public View createView(int position, int viewType, ViewGroup parent, LayoutInflater inflater) {
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            return adapter.createView(mContext, position, parent, inflater);
        }
        return null;
    }

    @Override
    final public ViewHolder getViewHolder(int viewType, View view) {
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            return adapter.getViewHolder(view);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    final public void onBindData(ViewHolder holder, int viewType) {
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            adapter.onBindData(mContext, holder, holder.getEntity());
        }
    }

    @Override
    public int getViewTypeCount() {
        return mTypeAdapters.size();
    }

    @Override
    final  public int getItemViewType(int position) {
        return getItemDataType(position);
    }

    /**
     * 获取数据类型
     * @param position 位置
     * @return 数据类型
     */
    public abstract int getItemDataType(int position);
}

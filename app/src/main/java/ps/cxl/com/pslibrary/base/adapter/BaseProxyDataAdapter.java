package ps.cxl.com.pslibrary.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

import ps.cxl.com.core.bean.BaseEntity;

/**
 * 数据代理 基础adapter
 * Created by wanglx on 16/8/29.
 */
@SuppressWarnings("unused")
public abstract class BaseProxyDataAdapter<T extends BaseEntity>  extends BaseAdapter {
    private SparseArray<IViewItemTypeAdapter> mTypeAdapters = new SparseArray<>();
    protected IListDataProxy<T>  mDataProxy ;
    private LayoutInflater mInflater;
    protected Context mContext;

    public BaseProxyDataAdapter(Context context, IListDataProxy<T> proxy) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDataProxy = proxy;
    }

    public BaseProxyDataAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setTypeAdapters(SparseArray<IViewItemTypeAdapter> typeAdapters) {
        mTypeAdapters = typeAdapters;
    }

    public void addTypeAdapter(IViewItemTypeAdapter adapter) {
        mTypeAdapters.put(adapter.getType(), adapter);
    }

    public void setDataProxy(IListDataProxy<T> proxy){
        mDataProxy = proxy;
    }

    public void setData(List<? extends T> data, int type){
        mDataProxy.set(data, type);
        notifyDataSetInvalidated();
    }

    public void addData(T data, int type){
        mDataProxy.add(data, type);
        notifyDataSetInvalidated();
    }

    public void addAll(List<? extends T> data, int type){
        mDataProxy.addAll(data, type);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataProxy.size();
    }

    @Override
    public T getItem(int position) {
        return mDataProxy.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
   final public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        BaseDataAdapter.ViewHolder holder = null;
        int viewType =  getItemViewType(position);

        if (convertView == null) {
            view = createView(position, viewType, parent, mInflater);
        }

        if (view != null) {
            holder = (BaseDataAdapter.ViewHolder) view.getTag();
        }

        if (holder == null) {
            holder = getViewHolder(viewType, view);
        }

        if (view != null) {
            view.setTag(holder);
        }

        if (holder != null) {
            holder.mPosition = position;
            holder.mViewType = viewType;
            holder.mEntity = getItem(holder.getPosition());

            onBindData(holder, viewType);
        }

        return view;
    }

    /**
     * 创建view
     * @param position 位置
     * @param parent  父view
     * @param inflater 加载器
     * @param viewType view 类型
     */
    final public View createView(int position, int viewType, ViewGroup parent, LayoutInflater inflater){
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            return adapter.createView(mContext, position, parent, inflater);
        }
        return null;
    }

    /**
     * 获取 viewholder
     * @param viewType view类型
     */
    final public BaseDataAdapter.ViewHolder getViewHolder(int viewType, View view){
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            return adapter.getViewHolder(view);
        }
        return null;
    }

    /**
     * 绑定数据到 viewholder
     * @param holder  viewholder
     */
    @SuppressWarnings("unchecked")
    final public void onBindData(BaseDataAdapter.ViewHolder holder ,int viewType){
        IViewItemTypeAdapter adapter = mTypeAdapters.get(viewType);
        if(adapter != null){
            adapter.onBindData(mContext, holder, getItem(holder.getPosition()));
        }
    }

    @Override
    public int getViewTypeCount() {
        return mTypeAdapters.size();
    }

    @Override
    final public int getItemViewType(int position) {
        return getItemDataType(position);
    }

    /**
     * 获取数据类型
     * @param position 位置
     * @return 数据类型
     */
    public abstract int getItemDataType(int position);
}

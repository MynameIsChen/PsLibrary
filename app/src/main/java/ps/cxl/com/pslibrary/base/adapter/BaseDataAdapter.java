package ps.cxl.com.pslibrary.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ps.cxl.com.common.util.CheckUtil;
import ps.cxl.com.core.bean.BaseEntity;

/**
 * 基础 list view 适配器
 * Created by wanglx on 16/8/4
 */
@SuppressWarnings("unused")
public abstract class BaseDataAdapter<T extends BaseEntity> extends BaseAdapter {
    protected List<T> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    protected Context mContext;

    public BaseDataAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public BaseDataAdapter(List<? extends T> list, Context context) {
        mList.addAll(list);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(List<? extends T> list) {
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetInvalidated();
    }

    public void setData(int index, T t) {
        if (mList != null) {
            mList.set(index, t);
        }
        notifyDataSetChanged();
    }

    public void addData(List<? extends T> list) {
        if (list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position < 0 || position > mList.size()) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        int viewType =  getItemViewType(position);

        if (convertView == null) {
            view = createView(position, viewType, parent, mInflater);
        }

        if (view != null) {
            holder = (ViewHolder) view.getTag();
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

    public static class ViewHolder {
        protected OnItemClickedListener mItemClickedListener;
        protected int mPosition;
        protected boolean mIsLast = false;
        protected BaseEntity mEntity = null;
        protected int mViewType = -1;
        protected View mView;

        public ViewHolder(View view, OnItemClickedListener listener) {
            mView = view;
            ButterKnife.bind(this, view);
            mItemClickedListener = listener;
        }

        public void post(int action) {
            if (CheckUtil.isNonEmpty(mItemClickedListener)) {
                mItemClickedListener.onItemClick(action, 0, this);
            }
        }

        public void post(int action, int arg) {
            if (CheckUtil.isNonEmpty(mItemClickedListener)) {
                mItemClickedListener.onItemClick(action, arg, this);
            }
        }

        public int getPosition() {
            return mPosition;
        }

        public BaseEntity getEntity() {
            return mEntity;
        }

        public int getViewType() {
            return mViewType;
        }

        public void setViewType(int viewType) {
            mViewType = viewType;
        }
    }

    /**
     * 创建view
     *
     * @param position 位置
     * @param parent   父view
     * @param inflater 加载器
     * @param viewType view 类型
     */
    public abstract View createView(int position, int viewType, ViewGroup parent, LayoutInflater inflater);

    /**
     * 获取 viewholder
     *
     * @param viewType view类型
     */
    public abstract ViewHolder getViewHolder(int viewType, View view);

    /**
     * 绑定数据到viewholder
     *
     * @param holder viewholder
     */
    public abstract void onBindData(ViewHolder holder, int viewType);
}

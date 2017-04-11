package ps.cxl.com.pslibrary.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * view项类型适配器
 * Created by wanglx on 16/8/4.
 */
public interface IViewItemTypeAdapter<T> {

    /**
     * 获取view类型
     */
    int getType();

    /**
     * 创建view
     * @param position 位置
     * @param parent  父view
     * @param inflater 加载器
     */
    View createView(Context context, int position, ViewGroup parent, LayoutInflater inflater);

    /**
     * 获取 view holder
     */
    BaseDataAdapter.ViewHolder getViewHolder(View view);

    /**
     * 绑定数据到 view holder
     * @param holder  view holder
     */
    void onBindData(Context context, BaseDataAdapter.ViewHolder holder, T data);
}

package ps.cxl.com.pslibrary.base.adapter;

/**
 *  项点击事件监听
 * Created by wanglx on 16/8/22.
 */
public interface OnItemClickedListener {

    /**
     * view 项被点击 已经作废
     * @param position 在列表中的位置
     * @param arg0 自定义 参数0
     * @param arg1 自定义 参数1
     * @param arg2 自定义 参数2
     */
    @Deprecated
    void onItemClick(int viewType, int position, long arg0, int arg1, String arg2);


    /**
     * view 项被点击
     * @param action 点击行为
     * @param arg 自定义参数
     * @param holder view holder
     */
    void onItemClick(int action, int arg, BaseDataAdapter.ViewHolder holder);

}

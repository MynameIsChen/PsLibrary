package ps.cxl.com.pslibrary.base.adapter;


import java.util.List;

import ps.cxl.com.core.bean.BaseEntity;

/**
 * list 数据代理
 * Created by wanglx on 16/8/27.
 */
public interface IListDataProxy<T extends BaseEntity> {

    /**
     * 设置数据
     */
    void set(List<? extends T> data, int type);

    /**
     * 添加数据
     */
    void add(T data, int type);

    void addAll(List<? extends T> data, int type);

    int size();

    T get(int position);

    List<T> toList();
}

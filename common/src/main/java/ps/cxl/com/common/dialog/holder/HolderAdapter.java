package ps.cxl.com.common.dialog.holder;

import android.widget.BaseAdapter;

import ps.cxl.com.common.dialog.listener.OnHolderListener;


public interface HolderAdapter extends Holder {

    void setAdapter(BaseAdapter adapter);

    void setOnItemClickListener(OnHolderListener listener);
}

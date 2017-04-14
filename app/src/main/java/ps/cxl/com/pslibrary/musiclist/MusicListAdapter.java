package ps.cxl.com.pslibrary.musiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ps.cxl.com.core.bean.MusicListEntity;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.adapter.OnItemClickedListener;
import ps.cxl.com.pslibrary.base.adapter.SingleTypeViewAdapter;

import java.util.List;

public class MusicListAdapter extends SingleTypeViewAdapter<MusicListEntity> {

    public MusicListAdapter(List<MusicListEntity> list, Context context) {
        super(list, context);
    }

    public MusicListAdapter(Context context) {
        super(context);
    }

    @Override
    public View createView(ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_adapter_item__music_list, null);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MusicListViewHolder(view,mOnItemClickedListener);
    }

    @Override
    public void onBindData(ViewHolder holder) {
        MusicListViewHolder musiclistViewHolder =
                (MusicListViewHolder) holder;
        MusicListEntity entity = getItem(musiclistViewHolder.getPosition());
        // TODO: 绑定数据
    }

    static class MusicListViewHolder extends ViewHolder {
        public MusicListViewHolder(View view, OnItemClickedListener listener) {
            super(view,listener);
        }
    }
}

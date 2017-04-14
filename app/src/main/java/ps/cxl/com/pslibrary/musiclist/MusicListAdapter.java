package ps.cxl.com.pslibrary.musiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.core.bean.MusicEntity;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.adapter.OnItemClickedListener;
import ps.cxl.com.pslibrary.base.adapter.SingleTypeViewAdapter;

public class MusicListAdapter extends SingleTypeViewAdapter<MusicEntity> {

    public MusicListAdapter(List<MusicEntity> list, Context context) {
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
        return new MusicListViewHolder(view, mOnItemClickedListener);
    }

    @Override
    public void onBindData(ViewHolder holder) {
        MusicListViewHolder musiclistViewHolder = (MusicListViewHolder) holder;
        MusicEntity entity = getItem(musiclistViewHolder.getPosition());
        MusicListViewHolder viewHolder = (MusicListViewHolder) holder;
        viewHolder.mIcon.setBackgroundResource(R.drawable.ic_launcher);
        viewHolder.mTitle.setText(entity.getTitle());
        viewHolder.mArtist.setText(entity.getArtist().getName());
    }

    static class MusicListViewHolder extends ViewHolder {
        @BindView(R.id.icon)
        ImageView mIcon;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.artist)
        TextView mArtist;

        public MusicListViewHolder(View view, OnItemClickedListener listener) {
            super(view, listener);
            ButterKnife.bind(this,view);
        }
    }
}

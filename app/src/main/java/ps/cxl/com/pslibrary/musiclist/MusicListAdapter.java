package ps.cxl.com.pslibrary.musiclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.core.bean.MusicEntity;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.adapter.OnItemClickedListener;
import ps.cxl.com.pslibrary.base.adapter.SingleTypeViewAdapter;

public class MusicListAdapter extends SingleTypeViewAdapter<MusicEntity> {
    public static final int MUSIC_ITEM = 0;
    public static final int MUSIC_MORE = 1;

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

        viewHolder.mIndex.setText(String.valueOf(viewHolder.getPosition() + 1));
        viewHolder.mTitle.setText(entity.getTitle());
        viewHolder.mArtist.setText(entity.getArtist().getName());
    }

    static class MusicListViewHolder extends ViewHolder {
        @BindView(R.id.index)
        TextView mIndex;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.artist)
        TextView mArtist;
        @BindView(R.id.more)
        TextView mMore;

        public MusicListViewHolder(View view, final OnItemClickedListener listener) {
            super(view, listener);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(MUSIC_ITEM, getPosition(), null);
                }
            });
            mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(MUSIC_MORE, getPosition(), null);
                }
            });
        }
    }
}

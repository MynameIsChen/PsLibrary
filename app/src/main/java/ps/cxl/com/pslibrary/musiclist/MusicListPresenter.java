package ps.cxl.com.pslibrary.musiclist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import ps.cxl.com.common.util.CheckUtil;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.bean.MusicListEntity;

public class MusicListPresenter extends MusicListContract.Presenter {
    private MusicListContract.View mView;

    private MusicListPresenter(MusicListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    static MusicListPresenter create(MusicListContract.View view) {
        return new MusicListPresenter(view);
    }

    @Override
    MusicListEntity findMusicList(ContentResolver resolver) {
        find(resolver);
        return null;
    }

    private void find(ContentResolver resolver) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.MIME_TYPE + "=? or " + MediaStore.Audio.Media.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{"audio/mp3"};
        String sortOrder = MediaStore.Audio.Media.DATE_MODIFIED + " desc";

        Cursor cursor = resolver.query(uri, null, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                parserItem(cursor);
            }
        }
    }

    private void parserItem(Cursor cursor) {
        String[] columns = cursor.getColumnNames();
        StringBuffer info = null;
        for (String key : columns) {
            if (CheckUtil.isNonEmpty(key)) {
                int index = cursor.getColumnIndex(key);
                String value = cursor.getString(index);
                info.append(key).append(":");
                if (CheckUtil.isNonEmpty(value)) {
                    info.append(value).append("/n");
                }
            }
        }
        Lg.d("parserItem", "item==" + info);
    }
}

package ps.cxl.com.pslibrary.musiclist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ps.cxl.com.common.util.CheckUtil;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.bean.CommonTypeEntity;
import ps.cxl.com.core.bean.MusicEntity;
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
    void findMusicList(ContentResolver resolver) {
        mView.setMusicList(find(resolver));
    }

    MusicListEntity find(ContentResolver resolver) {
        MusicListEntity entity = new MusicListEntity();
        List<MusicEntity> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.IS_MUSIC;

        Cursor cursor = resolver.query(uri, null, null, null, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(parserItem(cursor));
            }
        }
        entity.setList(list);
        return entity;
    }

    private MusicEntity parserItem(Cursor cursor) {
        MusicEntity entity;
        String[] columns = cursor.getColumnNames();
        Map<String, Object> map = new HashMap<>();
        StringBuffer info = new StringBuffer();
        long id = cursor.getLong(cursor.getColumnIndex(Media._ID));
        String data = cursor.getString(cursor.getColumnIndex(Media.DATA));
        String title = cursor.getString(cursor.getColumnIndex(Media.TITLE));
        int size = cursor.getInt(cursor.getColumnIndex(Media.SIZE));
        long duration = cursor.getLong(cursor.getColumnIndex(Media.DURATION));
        int isMusic = cursor.getInt(cursor.getColumnIndex(Media.IS_MUSIC));
        int isAlarm = cursor.getInt(cursor.getColumnIndex(Media.IS_ALARM));
        String artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST));
        long artistId = cursor.getLong(cursor.getColumnIndex(Media.ARTIST_ID));
        String album = cursor.getString(cursor.getColumnIndex(Media.ALBUM));
        long albumId = cursor.getLong(cursor.getColumnIndex(Media.ALBUM_ID));
        int type = isAlarm == 1 ? 1 : 0;
        if (isMusic == 1) {
            type = 0;
        } else if (isAlarm == 1) {
            type = 1;
        }

        CommonTypeEntity artistEntity = new CommonTypeEntity(artist, artistId);
        CommonTypeEntity albumEntity = new CommonTypeEntity(album, albumId);
        entity = new MusicEntity(id, data, title, size, duration, artistEntity, albumEntity, type);
        for (String key : columns) {
            if (CheckUtil.isNonEmpty(key)) {
                int index = cursor.getColumnIndex(key);
                String value = cursor.getString(index);
                info.append(key).append(":");
                if (CheckUtil.isNonEmpty(value)) {
                    info.append(value).append("\n");
                }
                map.put(key, value);
            }
        }
        entity.setSource(map);
        Lg.d("parserItem", "item==" + info);
        return entity;
    }
}

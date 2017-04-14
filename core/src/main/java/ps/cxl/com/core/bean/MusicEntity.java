package ps.cxl.com.core.bean;

import java.util.Map;

import ps.cxl.com.common.util.NonNullUtil;

/**
 * Created : chenxianglin on 17/4/14.
 */

public class MusicEntity implements BaseEntity {
    private long id;
    private String data;
    private String title;
    private long size;
    private long duration;
    private Map<String, Object> source;
    private CommonTypeEntity artist;
    private CommonTypeEntity album;
    private int type;//0:music;1:alarm;

    public MusicEntity() {

    }

    public MusicEntity(long id, String data, String title, long size, long duration, CommonTypeEntity artist, CommonTypeEntity album, int type) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.type = type;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public String getData() {
        return NonNullUtil.get(data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return NonNullUtil.get(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public CommonTypeEntity getArtist() {
        return artist;
    }

    public void setArtist(CommonTypeEntity artist) {
        this.artist = artist;
    }

    public CommonTypeEntity getAlbum() {
        return album;
    }

    public void setAlbum(CommonTypeEntity album) {
        this.album = album;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

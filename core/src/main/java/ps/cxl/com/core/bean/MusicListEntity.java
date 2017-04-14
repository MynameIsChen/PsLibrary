package ps.cxl.com.core.bean;

import java.util.List;

import ps.cxl.com.common.util.NonNullUtil;

/**
 * Created : chenxianglin on 17/4/14.
 */

public class MusicListEntity implements BaseEntity {
    private List<MusicEntity> list;

    public MusicListEntity(){}

    public MusicListEntity(List<MusicEntity> list) {
        this.list = list;
    }

    public List<MusicEntity> getList() {
        return NonNullUtil.get(list);
    }

    public void setList(List<MusicEntity> list) {
        this.list = list;
    }
}

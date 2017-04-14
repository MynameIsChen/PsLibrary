package ps.cxl.com.core.bean;

import ps.cxl.com.common.util.NonNullUtil;

/**
 * Created : chenxianglin on 17/4/14.
 */

public class CommonTypeEntity implements BaseEntity {
    private String name;
    private long id;

    public CommonTypeEntity() {

    }

    public CommonTypeEntity(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return NonNullUtil.get(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

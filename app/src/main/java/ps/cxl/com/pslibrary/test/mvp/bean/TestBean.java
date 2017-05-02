package ps.cxl.com.pslibrary.test.mvp.bean;

import ps.cxl.com.core.bean.BaseEntity;

/**
 * Created : chenxianglin on 17/5/2.
 */

public class TestBean implements BaseEntity {
    private long id;
    private String name;
    private String info;

    public TestBean(long id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

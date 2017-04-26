package ps.cxl.com.pslibrary.test.mvp.bean;

import ps.cxl.com.common.util.NonNullUtil;
import ps.cxl.com.core.bean.BaseEntity;

/**
 * Created : chenxianglin on 17/4/21.
 */

public class UserBean implements BaseEntity {
    private String firstName;
    private String lastName;

    public UserBean(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return NonNullUtil.get(firstName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return NonNullUtil.get(lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

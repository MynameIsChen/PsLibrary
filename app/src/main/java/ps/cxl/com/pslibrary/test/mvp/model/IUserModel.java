package ps.cxl.com.pslibrary.test.mvp.model;

import ps.cxl.com.pslibrary.test.mvp.bean.UserBean;

/**
 * Created : chenxianglin on 17/4/21.
 */

public interface IUserModel {
    void setId(long id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    long getId();

    UserBean load(long id);
}

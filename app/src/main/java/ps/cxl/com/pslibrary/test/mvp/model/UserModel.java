package ps.cxl.com.pslibrary.test.mvp.model;

import ps.cxl.com.pslibrary.test.mvp.bean.UserBean;

/**
 * Created : chenxianglin on 17/4/21.
 */

public class UserModel implements IUserModel {
    private UserBean user = null;
    private long id = 0;

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setFirstName(String firstName) {
        if (user != null) {
            user.setFirstName(firstName);
        }
    }

    @Override
    public void setLastName(String lastName) {
        if (user != null) {
            user.setLastName(lastName);
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public UserBean load(long id) {
        this.id = id;
        user = new UserBean("firstName", "lastName");
        return user;
    }
}

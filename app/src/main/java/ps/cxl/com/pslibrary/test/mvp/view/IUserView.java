package ps.cxl.com.pslibrary.test.mvp.view;

/**
 * Created : chenxianglin on 17/4/21.
 */

public interface IUserView {
    long getId();

    String getFirstName();

    String getLastName();

    void setFirstName(String firstName);

    void setLastName(String lastName);
}

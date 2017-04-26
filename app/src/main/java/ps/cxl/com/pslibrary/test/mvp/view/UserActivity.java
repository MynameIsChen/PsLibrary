package ps.cxl.com.pslibrary.test.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.test.mvp.presenter.UserPresenter;

/**
 * Created : chenxianglin on 17/4/21.
 */

public class UserActivity extends Activity implements View.OnClickListener, IUserView {
    UserPresenter mPresenter;
    @BindView(R.id.id)
    EditText mId;
    @BindView(R.id.firstName)
    EditText mFirstName;
    @BindView(R.id.lastName)
    EditText mLastName;
    @BindView(R.id.save)
    TextView mSave;
    @BindView(R.id.load)
    TextView mLoad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPresenter = new UserPresenter(this);
        mSave.setOnClickListener(this);
        mLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                mPresenter.saveUser(getId(), getFirstName(), getLastName());
                break;
            case R.id.load:
                mPresenter.loadUser(getId());
                break;
        }
    }

    @Override
    public long getId() {
        return Long.valueOf(mId.getText().toString());
    }

    @Override
    public String getFirstName() {
        return mFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return mLastName.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        mFirstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        mLastName.setText(lastName);
    }
}

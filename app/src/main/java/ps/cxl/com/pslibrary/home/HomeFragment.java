package ps.cxl.com.pslibrary.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.common.util.ToastUtil;
import ps.cxl.com.core.api.TestApi;
import ps.cxl.com.core.api.call.IApiCall;
import ps.cxl.com.core.api.call.IApiCallback;
import ps.cxl.com.core.api.manager.RestApiManager;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseFragment;
import ps.cxl.com.pslibrary.musiclist.MusicListActivity;
import ps.cxl.com.pslibrary.preview.PreviewActivity;
import ps.cxl.com.pslibrary.test.House;
import ps.cxl.com.pslibrary.test.IHouse;
import ps.cxl.com.pslibrary.test.ProxyHouse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment implements HomeContract.View, View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private HomeContract.Presenter mPresenter;
    private View mRoot;

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.info)
    TextView mInfo;
    @BindView(R.id.password)
    TextView mPassword;
    @BindView(R.id.safe)
    TextView mSafe;
    @BindView(R.id.set)
    TextView mSet;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.content)
    EditText mContent;

    private ProxyHouse mHouse;
    private House house;
    private IHouse mIHouse;
    private RestApiManager mManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomePresenter.create(this);
    }

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_home, null);
            ButterKnife.bind(this, mRoot);
        }
        init();
        return mRoot;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void init() {
        initProxy();
        initView();
    }

    private void initView() {
        mManager = RestApiManager.getInstance();
        mName.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mSafe.setOnClickListener(this);
        mSet.setOnClickListener(this);
    }

    private void initProxy() {
        house = new House("DownTon", 10);
        mHouse = new ProxyHouse(house);
        mIHouse = (IHouse) mHouse.newProxyInstance(new House("Proxy", 2333));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.name:
                Lg.d(TAG, "Do it.");
                mHouse.getHouseInfo();
                mHouse.signContract();
                mHouse.payFees();
                mIHouse.getHouseInfo();
                mIHouse.signContract();
                mIHouse.payFees();
                break;
            case R.id.info:
                getDetail(mContent.getText().toString());
                break;
            case R.id.password:
                getNewsDetail(mContent.getText().toString());
                break;
            case R.id.safe:
                PreviewActivity.lanuch(getContext());
                break;
            case R.id.set:
                MusicListActivity.launch(getContext());
                break;
        }
    }

    private void getDetail(String info) {
        long id = Long.parseLong(info);
        TestApi api = mManager.create(TestApi.class);
        Call<ResponseBody> call = api.getDetail(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                ResponseBody errorBody = response.errorBody();
                int code = response.code();
                String message = response.message();
                Headers headers = response.headers();
                boolean successful = response.isSuccessful();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Lg.d("onFailure");
            }
        });
    }

    private void getNewsDetail(String info) {
        long id = Long.parseLong(info);
        TestApi api = mManager.create(TestApi.class);
        IApiCall<Object> call = api.getNewsDetail(id);
        call.enqueue(new IApiCallback<Object>() {
            @Override
            public void success(Response<Object> response, Object o) {
                Object body = response.body();
                ResponseBody errorBody = response.errorBody();
                int code = response.code();
                String message = response.message();
                Headers headers = response.headers();
                boolean successful = response.isSuccessful();
            }

            @Override
            public void error(int errorType, int code, String message) {
                super.error(errorType, code, message);
            }
        });
    }

    public void toast() {
        ToastUtil.toast("toast from activity.");
    }
}

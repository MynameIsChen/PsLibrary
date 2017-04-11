package ps.cxl.com.pslibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.core.api.TestApi;
import ps.cxl.com.core.api.call.IApiCall;
import ps.cxl.com.core.api.call.IApiCallback;
import ps.cxl.com.core.api.manager.RestApiManager;
import ps.cxl.com.pslibrary.test.House;
import ps.cxl.com.pslibrary.test.IHouse;
import ps.cxl.com.pslibrary.test.ProxyHouse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
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
    }

    private void initProxy() {
        house = new House("DownTon", 10);
        mHouse = new ProxyHouse(house);
        mIHouse = (IHouse) mHouse.newProxyInstance(new House("Proxy", 2333));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test:
                Lg.d(TAG, "Do it.");
                mHouse.getHouseInfo();
                mHouse.signContract();
                mHouse.payFees();
                mIHouse.getHouseInfo();
                mIHouse.signContract();
                mIHouse.payFees();
                break;
            case R.id.detail:
                getDetail(mContent.getText().toString());
                break;
            case R.id.news:
                getNewsDetail(mContent.getText().toString());
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
}

package ps.cxl.com.pslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.pslibrary.test.House;
import ps.cxl.com.pslibrary.test.IHouse;
import ps.cxl.com.pslibrary.test.ProxyHouse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private TextView mTest;
    private ProxyHouse mHouse;
    private House house;
    private IHouse mIHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTest = (TextView) findViewById(R.id.test);
        mTest.setOnClickListener(this);
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
        }
    }
}

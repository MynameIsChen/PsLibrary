package ps.cxl.com.pslibrary.test;

import android.os.SystemClock;

import java.text.SimpleDateFormat;

import ps.cxl.com.common.util.Lg;

/**
 * Created : chenxianglin on 17/4/6.
 * 配置代理proxy则可以实现强制代理
 */

public class House implements IHouse {
    private final String TAG = "house";
    private String name;
    private double price;

    public House(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void getHouseInfo() {
        Lg.d(TAG, "house-info：name=" + name + ";price=" + price);
    }

    @Override
    public void signContract() {
        Lg.d(TAG, "Contract：" + name + "  signed at" + new SimpleDateFormat("HH:mm:ss").format(SystemClock.uptimeMillis()));
    }

    @Override
    public void payFees() {
        Lg.d(TAG, "Bill：name-" + name + "  $ " + price);
    }
}

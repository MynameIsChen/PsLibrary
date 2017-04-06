package ps.cxl.com.pslibrary.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import ps.cxl.com.common.util.Lg;

/**
 * Created : chenxianglin on 17/4/6.
 * InvocationHandler实现动态代理
 * 否则为普通代理
 */

public class ProxyHouse implements IHouse,InvocationHandler {
    private final String TAG = "ProxyHouse";
    private IHouse mHouse;
    private Object targetObj;

    public Object newProxyInstance(Object object){
        this.targetObj = object;
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),targetObj.getClass().getInterfaces(),this);
    }

    /**
     * 虚拟代理配置
     * 基于代理模式又做了延迟加载来节省内存,但是如果某个对象要在多个没有固定时序地方使用的时候就要进行判空,也会一定程度上牺牲性能(有点像代理模式+懒汉模式)
     * */
    public ProxyHouse() {
        if (mHouse == null) {
            mHouse = new House("default", 1000);
        }
    }

    public ProxyHouse(IHouse house) {
        mHouse = house;
    }

    @Override
    public void getHouseInfo() {
        Lg.d(TAG, "searching");
        mHouse.getHouseInfo();
        Lg.d(TAG, "search finished");
    }

    @Override
    public void signContract() {
        Lg.d(TAG, "prepare contract");
        mHouse.signContract();
    }

    @Override
    public void payFees() {
        mHouse.payFees();
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object obj;
        Lg.d(TAG,"method name："+method.getName());
        obj = method.invoke(targetObj,objects);
        return obj;
    }
}

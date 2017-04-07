package ps.cxl.com.core.api.manager;

/**
 *  默认地址
 * Created by wanglx on 16/4/11.
 */
public class DefaultHostSelectionInterceptor extends HostSelectionInterceptor {
    public static final String TEST_URL = "https://testapi.ooent.cn/api/";
    public static final String ONLINE_URL = "https://api.quanminxingtan.com/api/";

    public DefaultHostSelectionInterceptor(HostSelectionFactory.ApiType apiType) {
        super(apiType);
    }

    @Override
    public String getTestUrl() {
        return TEST_URL;
    }

    @Override
    public HostSelectionFactory.ApiType getType() {
        return HostSelectionFactory.ApiType.DEFAULT;
    }

    @Override
    public String getOnlineUrl() {
        return ONLINE_URL;
    }
}

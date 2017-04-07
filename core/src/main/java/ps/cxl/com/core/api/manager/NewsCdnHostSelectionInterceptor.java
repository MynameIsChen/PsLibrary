package ps.cxl.com.core.api.manager;

/**
 * 新闻cdn地址
 * Created by wanglx on 16/4/11.
 */
class NewsCdnHostSelectionInterceptor extends HostSelectionInterceptor {
    private String TEST_URL;
    private String ONLINE_URL;

    public NewsCdnHostSelectionInterceptor(HostSelectionFactory.ApiType apiType) {
        super(apiType);
        if (true) {
            TEST_URL = DefaultHostSelectionInterceptor.TEST_URL;
            ONLINE_URL = DefaultHostSelectionInterceptor.ONLINE_URL;
        }
    }

    @Override
    public String getTestUrl() {
        return TEST_URL;
    }

    @Override
    public HostSelectionFactory.ApiType getType() {
        return HostSelectionFactory.ApiType.NEWS_CDN;
    }

    @Override
    public String getOnlineUrl() {
        return ONLINE_URL;
    }
}

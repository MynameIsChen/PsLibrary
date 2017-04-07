package ps.cxl.com.core.api;


public class Constants {
    public static final String STATIC_PARAMS_HEADER = "X-Static-Params";
    public static final String X_AUTH_TOKEN_HEADER = "X-Auth-Token";
    public static final  int MAX_AGE = 10;
    public final static int MAX_STALE =  60 * 60 * 24 * 28; // 离线时缓存保存一个月
    public static final int CACHE_SIZE = 20 * 1024 * 1024;//20M

    /**
     * 不走缓存
     */
    public static final String NO_CACHE_HEADER = "Cache-Control: no-cache";

    /**
     * 一分钟内缓存有效
     */
    public static final String CACHE_HEADER = "Cache-Control: public, max-age=" + MAX_AGE;

    /**
     * 只走缓存 缓存28天
     */
    public static final String ONLY_CACHE_HEADER = "Cache-Control: public, only-if-cached, max-stale=" + MAX_STALE;

    /**
     * 文件下载地址
     */
    public interface DOWNLOAD_FILE{
        String URL = "http://api.quanminxingtan.com/api/";
        String TEST_URL = "http://testapi.ooent.cn/api/";
    }
}
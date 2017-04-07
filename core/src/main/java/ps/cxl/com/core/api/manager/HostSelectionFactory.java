package ps.cxl.com.core.api.manager;

/**
 * api选择器 工厂
 * Created by wanglx on 16/4/11.
 */
public class HostSelectionFactory {

    public enum ApiType {
        DEFAULT,
        NEWS_CDN
    }

    public static  HostSelectionInterceptor getInterceptor(ApiType type){
        switch (type){
            case DEFAULT:
                return new DefaultHostSelectionInterceptor(type);
            case NEWS_CDN:
                return new NewsCdnHostSelectionInterceptor(type);
            default:
                throw new RuntimeException("no such api type :" + type.name());
        }
    }
}

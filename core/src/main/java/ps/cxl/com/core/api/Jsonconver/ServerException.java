package ps.cxl.com.core.api.Jsonconver;

/**
 * 服务器异常
 */
public class ServerException extends RuntimeException {

    private int code;

    public ServerException(String detailMessage , int code) {
        super(detailMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}

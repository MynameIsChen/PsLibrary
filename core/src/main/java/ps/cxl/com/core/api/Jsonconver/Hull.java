package ps.cxl.com.core.api.Jsonconver;

/**
 * api 接口壳
 */
public class Hull<T> {
    public int code;
    public String msg;
    public T data;

    public Hull(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }
}

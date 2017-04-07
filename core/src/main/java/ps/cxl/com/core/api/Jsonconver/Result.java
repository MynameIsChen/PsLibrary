package ps.cxl.com.core.api.Jsonconver;

/**
 * api请求结果
 */
public class Result {
    private int code;
    private String msg;
    private boolean success;
    public Result(int code, boolean success, String message) {
        this.code = code;
        this.msg = message;
        this.success = success;
    }

    public boolean isSucceed(){
        return success;
    }

    public int getCode(){
        return code;
    }

    public String getMessage() {
        return msg;
    }
}

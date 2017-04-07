package ps.cxl.com.core.api.Jsonconver;

import org.json.JSONException;

/**
 * 自定义解析器接口
 */
public interface IParser<T> {

    /**
     * 解析json
     */
    T parse(String json) throws JSONException;
}

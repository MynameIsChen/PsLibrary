package ps.cxl.com.core.api.Jsonconver;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 响应json转换
 */
final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, Hull<T>> {
    private final TypeAdapter<T> mAdapter;
    private final Type mType;
    private IParser<T> mParser = null;

    public JsonResponseBodyConverter(Type type, TypeAdapter<T> adapter) {
        this.mAdapter = adapter;
        this.mType = type;
    }

    public JsonResponseBodyConverter(Type type, TypeAdapter<T> adapter, IParser<T> parser) {
        this.mAdapter = adapter;
        this.mType = type;
        this.mParser = parser;
    }

    @Override
    public Hull<T> convert(ResponseBody value) throws IOException {
        try {
            return getResult(value.string());
        } finally {
            value.close();
        }
    }

    @SuppressWarnings("unchecked")
    private Hull<T> getResult(String response) throws IOException {
        try {
            T t;
            JSONObject jsonObject = new JSONObject(response);
            int code = jsonObject.optInt("code");
            String message = jsonObject.optString("msg");
            boolean success = jsonObject.optBoolean("success");
            if(mType == Result.class){
                t = (T) new Result(code, success, message);
            } else {
                if (!success) {
                    throw new ServerException(message, code);
                }
                String result = jsonObject.optString("data");
                if(mType == String.class){
                    t = (T) result;
                } else  {
                    if(mParser != null){
                        t = mParser.parse(result);
                    }else {
                        t = mAdapter.fromJson(result);
                    }
                }
            }
            return new Hull<>(code, message, t);
        } catch (NullPointerException | JSONException | IOException | JsonParseException e ) {
            throw new JsonConverterException(e.getMessage(), e);
        }
    }
}
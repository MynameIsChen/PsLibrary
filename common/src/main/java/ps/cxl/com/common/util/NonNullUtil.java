package ps.cxl.com.common.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取非空对象
 * Created by wanglx on 16/8/31.
 */
public class NonNullUtil {

    public static String get(String str) {
        return  str == null ? "" : str;
    }

    public static <T> List<T> get(List<T> list) {
        return  list == null ? new ArrayList<T>() : list;
    }

    public static Map get(Map map) {
        return  map == null ? new HashMap<>() : map;
    }
}

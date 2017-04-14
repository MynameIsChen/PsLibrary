package ps.cxl.com.common.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created : chenxianglin on 17/4/7.
 */

public class CheckUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNonEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean isNonEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isEmpty(Object[] o) {
        return o == null || o.length == 0;
    }

    public static boolean isNonEmpty(Object[] o) {
        return !isEmpty(o);
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNonEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Set set) {
        return set == null || set.size() == 0;
    }

    public static boolean isNonEmpty(Set set) {
        return !isEmpty(set);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNonEmpty(Map map) {
        return !isEmpty(map);
    }
}

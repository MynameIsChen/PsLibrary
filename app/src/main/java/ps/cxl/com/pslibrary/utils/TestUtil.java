package ps.cxl.com.pslibrary.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.pslibrary.test.mvp.bean.TestBean;

/**
 * Created : chenxianglin on 17/5/2.
 */

public class TestUtil {

    public static void test() {
        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean(1, "小明", "1info4"));
        list.add(new TestBean(2, "小花", "2info2"));
        list.add(new TestBean(3, "小黑", "3info3"));
        list.add(new TestBean(4, "小白", "4info5"));
        list.add(new TestBean(5, "小猪", "5info1"));
        Collections.sort(list, new MapComparator());
        for (TestBean bean : list) {
            Lg.d("test", bean.getName() + ":" + bean.getInfo());
        }
    }

    //比较，正数，可以理解为o1>o2则升序排列(o2，o1)
    private static class MapComparator implements Comparator<TestBean> {

        @Override
        public int compare(TestBean o1, TestBean o2) {
            Lg.d("test", o1.getName() + ":" + o2.getName());
            int value = o1.getName().compareTo(o2.getName());
            Lg.d("test", "value:" + value);
            return value;
        }
    }
}

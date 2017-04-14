package ps.cxl.com.common.util;

import android.text.TextPaint;
import android.widget.TextView;

/**
 * Created : chenxianglin on 16/9/18.
 */
public class CharUtil {

    /**
     * 取某段字符串具体字数限制内的内容,utf-8下的文字以3个字节计算,字母与数字以1个字节计算
     */

    public static String getIndexNum(String info, int num) {
        String result = "";
        int allNum = 0;
        int index = 0;

        if (CheckUtil.isNonEmpty(info)) {
            char[] chars = info.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                index = i;
                String s = chars[i] + "";
                allNum += s.getBytes().length;
                if (allNum > num * 3) {
                    index = i - 1;
                    break;
                }
            }

            result = info.substring(0, index + 1);
        }
        return result;
    }

    /**
     * 获取最大num位长度的文字,多余则-2+...实现
     *
     * @param info   内容
     * @param num    保留长度
     * @param max    最大长度
     * @param suffix 后缀
     */
    public static String getSampleInfo(String info, int num, int max, String suffix) {
        int allNum = 0;
        int index = 0;
        boolean moreThan = false;

        if (CheckUtil.isNonEmpty(info)) {
            char[] chars = info.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                String s = chars[i] + "";
                allNum += s.getBytes().length;
                if (index == 0 && allNum > num * 3) {
                    index = i;
                }
                if (allNum > max * 3) {
                    moreThan = true;
                    break;
                }
            }

            if (index > 0 && info.length() > index && moreThan) {
                info = info.substring(0, index) + suffix;
            }
        }
        return info;
    }

    public static String getSampleInfo(String info) {
        return getSampleInfo(info, 3, 5, "...");
    }

    public static String getEnSampleInfo(String info) {
        if (info.length() > 7) {
            info = info.substring(0, 5) + "...";
        }
        return info;
    }


    public static int getCharCount(String info) {
        int count = 0;
        if (!CheckUtil.isEmpty(info)) {
            char[] chars = info.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                String s = chars[i] + "";
                count += s.getBytes().length;
            }
        }
        return (count + 2) / 3;
    }

    /**
     * 计算以固定中文长度，控制控件宽度，来获取需要显示的内容
     */
    public static String getMaxString(TextView textView, String info, int num) {
        TextPaint paint = textView.getPaint();
        float itemWidth = paint.measureText("探");

        String result = "";
        int allWidth = 0;
        int index = 0;

        if (CheckUtil.isNonEmpty(info)) {
            char[] chars = info.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                String s = chars[i] + "";
                allWidth += paint.measureText(s);
                if (allWidth > num * itemWidth) {
                    break;
                } else {
                    index = i;
                }
            }

            result = info.substring(0, index + 1);
        }

        return result;
    }

    public static int getStringNum(TextView textView, String info) {
        TextPaint paint = textView.getPaint();
        float itemWidth = paint.measureText("探");
        float allWidth = 0;
        if (CheckUtil.isNonEmpty(info)) {
            allWidth = paint.measureText(info);
        }
        return (int) ((allWidth + itemWidth - 1) / itemWidth);
    }
}

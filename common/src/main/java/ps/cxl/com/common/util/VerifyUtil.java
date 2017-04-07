package ps.cxl.com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 * Created : chenxianglin on 16/9/29.
 */
public class VerifyUtil {
    /**
     * 校验电话号码
     */
    public static boolean verifyPhoneNum(String phoneNum) {
        if (CheckUtil.isEmpty(phoneNum)) {
            return false;
        }
        return phoneNum.matches("^1[34578]\\d{9}$") || phoneNum.matches("^+861\\d{10}$") || phoneNum.matches("^861\\d{10}$");
    }

    /**
     * 校验签名,中英文大小写,数字,-,_,限制20个字
     */
    public static boolean verifySign(String info) {
        if (info.length() >= 0 && info.length() <= 20 && verifyNickOrSign(info)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验昵称,2-15位
     */
    public static boolean verifyNick(String info) {
        if (CheckUtil.isEmpty(info)) {
            return false;
        } else {
            if (info.length() >= 2 && info.length() <= 15 && verifyNickOrSign(info)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean verifyNickOrSign(String info) {
        if (!info.contains("<") && !info.contains(">") && !info.contains("&lt;") && !info.contains("&gt;")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验密码的输入规则
     */
    public static boolean verifyPasswordRule(String info) {
        return !verifyChinese(info);
    }

    /**
     * 校验密码,密码6-20位字符,须同时包含数字和大小写字母,非中文
     */
    public static boolean verifyPassword(String info) {
        if (info.length() >= 6 && info.length() <= 20
                && verifyNum(info) && verifyEn(info) && !verifyChinese(info)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verifyNum(String info) {
        if (CheckUtil.isEmpty(info)) {
            return false;
        }
        String rex = "^.*[0-9].*$";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(info);
        return m.matches();
    }

    public static boolean verifyEn(String info) {
        if (CheckUtil.isEmpty(info)) {
            return false;
        }
        String rex = "^.*[a-zA-Z].*$";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(info);
        return m.matches();
    }

    /**
     * 校验颜色值
     *
     * @param color 例如: #d3d3d3
     */
    public static boolean verifyColor(String color) {
        if (CheckUtil.isEmpty(color)) {
            return false;
        }
        String rex = "\\#[0-9a-fA-F]{6}";
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(color);
        return m.matches();
    }

    /**
     * 校验中文
     */
    public static boolean verifyChinese(String s) {
        if (CheckUtil.isEmpty(s)) {
            return false;
        }
        String regEx = "^.*[\\u4e00-\\u9fa5].*$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 校验版本号
     */
    public static boolean verifyVersionName(String versionName) {
        if (CheckUtil.isEmpty(versionName)) {
            return false;
        }
        String regEx = "\\d+(\\.\\d+)*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(versionName);
        return m.matches();
    }

    /**
     * 校验邮箱
     */
    public static boolean verifyEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}

package com.xww.core.library.util.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xww
 * @desciption :
 * @date 2019/9/23
 * @time 23:25
 */
public class VerifyUtil {

    // 检查格式是否为手机号
    public static boolean checkPhone(String phoneNumber) {
        //正则表达式验证手机号
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        return Pattern.matches(regex, phoneNumber);
    }

    // 检查格式是否为身份证
    public static boolean checkIdentityCard(String idNumber) {
        //正则表达式验证身份证
        String regex = "([0-9]{17}([0-9]|X|x))|([0-9]{15})";
        return Pattern.matches(regex, idNumber);
    }

    // 检查格式是否为邮箱
    public static boolean checkEamil(String idNumber) {
        //正则表达式验证身份证
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, idNumber);
    }

}

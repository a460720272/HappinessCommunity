package com.xww.core.library.util.storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xww
 * @desciption : MD5 加密工具类
 * @date 2019/9/27
 * @time 21:12
 */
public class MD5Util {

    public static String getStringMD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}

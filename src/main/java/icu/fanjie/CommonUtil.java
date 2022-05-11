package icu.fanjie;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtil {
    public static String getMd5(String url) {
        return DigestUtils.md5Hex(url);
    }
}

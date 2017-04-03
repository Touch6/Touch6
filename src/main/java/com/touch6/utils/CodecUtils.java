package com.touch6.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by LONG on 2017/4/3.
 */
public class CodecUtils {
    public static String URLEncode(String src, String enc) {
        // 将普通字符创转换成application/x-www-from-urlencoded字符串
        try {
            String urlString = URLEncoder.encode(src, enc);
            return urlString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String URLDecode(String src, String enc) {
        // 将application/x-www-from-urlencoded字符串转换成普通字符串
        try {
            String urlString = URLDecoder.decode(src, enc);
            return urlString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

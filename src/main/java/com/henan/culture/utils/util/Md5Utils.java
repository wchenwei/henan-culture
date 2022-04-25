package com.henan.culture.utils.util;

import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;
import java.util.TreeMap;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-25 14:03
 **/
public class Md5Utils {

    public static final String Key = "2b656259152dfc42bf5d37a6fb9ca9f6";

    public static Digester md5Digester= new Digester(DigestAlgorithm.MD5);

    public static String encrypt(TreeMap<String,Object> paramMap){
        String content = generalSignContent(paramMap);
        return md5ToLower(content);
    }

    public static String generalSignContent(TreeMap<String,Object> paramMap){
        StringBuilder var1 = new StringBuilder();
        var1.append(Key);
        var1.append(buildSignExtra(paramMap));
        return var1.toString();
    }

    public static String buildSignExtra(TreeMap<String,Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,Object> var2 : paramMap.entrySet()){
            sb.append(var2.getKey()).append("=").append(var2.getValue());
            sb.append("&");
        }
        return sb.substring(0, sb.length()-1);
    }


    public static String md5ToLower(String signContent){
        return md5Digester.digestHex(URLUtil.encode(signContent)).toLowerCase();
    }
}

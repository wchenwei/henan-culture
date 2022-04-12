package com.henan.culture;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class ShiroTest {


    /**
     * 加密测试
     */
    @Test
    public void encryption() {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 1024;
        Object obj = new SimpleHash(hashAlgorithmName, credentials, null, hashIterations);
        System.out.println(obj);
    }
}

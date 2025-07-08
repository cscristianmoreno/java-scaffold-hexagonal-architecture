package co.com.bancolombia.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class HashUtil {

    private final static String MD5_HASH = "5484062a4be1ce5645eb414663e14f59";
    
    public static String hash(String value) throws NoSuchAlgorithmException {
        String hash = DigestUtils.md5Hex(value);
        return hash;
    }

    public static String getMd5Hash() {
        return MD5_HASH;
    }
}

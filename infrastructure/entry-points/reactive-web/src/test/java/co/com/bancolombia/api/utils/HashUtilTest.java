package co.com.bancolombia.api.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

public class HashUtilTest {

    private String md5Hash = HashUtil.getMd5Hash();

    @Test
    void testHash() throws NoSuchAlgorithmException {
        String hash = HashUtil.hash("250,25,10,100,100,7,8");

        /** Esto debería arrojar: (5484062a4be1ce5645eb414663e14f59)! */
        assertTrue(hash.matches(md5Hash));
    }
}

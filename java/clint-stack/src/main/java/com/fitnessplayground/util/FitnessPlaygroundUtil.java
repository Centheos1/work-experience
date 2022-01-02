package com.fitnessplayground.util;

import com.fitnessplayground.service.FitnessPlaygroundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
/**
 * Created by micheal on 27/02/2017.
 */
public class FitnessPlaygroundUtil {

    private static final Logger logger = LoggerFactory.getLogger(FitnessPlaygroundUtil.class);

    /**
     * Calculate the hmacDigest
     *
     * @param msg
     * @param keyString
     * @param algo hashing algorithm to use eg HMAC_SHA256, HMAC_MD5, HMAC_SHA1
     * @return
     */
    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException {}", e.getMessage());
        } catch (InvalidKeyException e) {
            logger.error("InvalidKeyException {}", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException {}", e.getMessage());
        }
//        logger.info("HMAC Digest: {}",digest);
        return digest;
    }

}

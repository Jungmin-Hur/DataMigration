package main.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	/**
	 * reference 
	 * http://www.asjava.com/core-java/java-md5-example/
	 */
	public static String getMd5(String msg) {
        try {
        	MessageDigest messageDigetst = MessageDigest.getInstance("MD5");
            BigInteger num = new BigInteger(1, messageDigetst.digest(msg.getBytes()));

            String md5Text = num.toString(16);
            while (md5Text.length() < 32) {
            	md5Text = "0" + md5Text;
            }
            return md5Text;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
}

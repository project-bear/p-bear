package info.bear.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Base64 加解密工具类
 * @author zhouchenguang
 */
public class Base64Utils {

    private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);
	/**
	 * Base64加密
	 * @param encrypt
	 * @return
	 */
	public static String encode(String encrypt){
		return new String(encode(encrypt.getBytes()));
	}

	/**
	 * Base64加密
	 * @param encrypt
	 * @return
	 */
	public static byte[] encode(byte[] encrypt){
		return Base64.encodeBase64(encrypt);
	}

	
	/**
	 * Base64解密
	 * @param decrypt
	 * @return
	 */
	public static String decrypt(String decrypt){
		return decrypt(decrypt.getBytes());
	}
	
	/**
	 * Base64解密
	 * @param decrypt
	 * @return
	 */
	public static String decrypt(byte[] decrypt){
		return new String(Base64.decodeBase64(decrypt));
	}
	public static void main(String[] args) {
		logger.info(Base64Utils.decrypt("eyJjb2xvck5hbWUiOiLngbDoibIiLCJwcmljZSI6MTAsInByb2R1Y3RDb2RlIjoiMDhXNTkwMyIsImFwcGx5U3RvcmVOYW1lIjoiNDAwMDIwMDQiLCJzaGlwbWVudFN0b3JlTmFtZSI6IuW5v%2BW3nuiQneWyl%2BS4h%2Bi%2BvuW5v%2BWcuuW6lyIsImNvbG9ySWQiOiIwOSIsInNpemVOYW1lIjoiUyIsInF1YW50aXR5IjoiMSIsImFwcGx5U3RvcmVDb2RlIjoiNDAwMDIwMDQiLCJwcm9kdWN0TmFtZSI6IkQy5b2p6Imy5o%2BQ6Iqx5q%2Bb6KGj6aKc6ImyIiwic2hpcG1lbnRTdG9yZUNvZGUiOiI0MDAwMjA0MiIsInNpemVJZCI6IlMiLCJza3VDb2RlIjoiMDhXNTkwMzA5UyJ9"));
		logger.info(Base64Utils.decrypt("eyJjb2xvck5hbWUiOiLngbDoibIiLCJwcmljZSI6MTAsInByb2R1Y3RDb2RlIjoiMDhXNTkwMyIsImFwcGx5U3RvcmVOYW1lIjoiNDAwMDIwMDQiLCJzaGlwbWVudFN0b3JlTmFtZSI6IuW5v+W3nuiQneWyl+S4h+i+vuW5v+WcuuW6lyIsImNvbG9ySWQiOiIwOSIsInNpemVOYW1lIjoiUyIsInF1YW50aXR5IjoiMSIsImFwcGx5U3RvcmVDb2RlIjoiNDAwMDIwMDQiLCJwcm9kdWN0TmFtZSI6IkQy5b2p6Imy5o+Q6Iqx5q+b6KGj6aKc6ImyIiwic2hpcG1lbnRTdG9yZUNvZGUiOiI0MDAwMjA0MiIsInNpemVJZCI6IlMiLCJza3VDb2RlIjoiMDhXNTkwMzA5UyJ9"));
	}
}

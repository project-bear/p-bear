package info.bear.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5加密工具类
 * @author zhouchenguang
 */
public class MD5Utils {
	
	private static final Logger LOG = LoggerFactory.getLogger(MD5Utils.class);

	/**
	 * MD5加密
	 * @param src
	 * @return
	 */
	public static String encrypt(String src){
		return encrypt(src.getBytes());
	}
	
	/**
	 * MD5加密
	 * @param srcByte
	 * @return
	 */
	public static String encrypt(byte[] srcByte){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digestByte = messageDigest.digest(srcByte);
			return Hex.encodeHexString(digestByte);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}

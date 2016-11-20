package info.bear.utils;

import java.util.List;

/**
 * 处理字符串工具类
 * 
 * @author zhouchenguang
 */
public class StringUtils {
	public static final String DELIM = ",";
	
	public static final String NULL = "null";

	/**
	 * 使用默认分隔符","拼接字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String join(List<? extends Object> list) {
		return join(list, DELIM);
	}

	/**
	 * 使用默认分隔符","拼接字符串
	 * 
	 * @param arr
	 * @return
	 */
	public static String join(Object[] arr) {
		return join(arr, DELIM);
	}

	/**
	 * 使用指定分隔符拼接字符串
	 * 
	 * @param list
	 * @param seperator
	 * @return
	 */
	public static String join(List<? extends Object> list, String seperator) {
		if (list == null || list.size() == 0)
			return "";
		Object[] t = new Object[0];
		return join(list.toArray(t), seperator);
	}

	/**
	 * 使用指定分隔符拼接字符串
	 * 
	 * @param arr
	 * @param seperator
	 * @return
	 */
	public static String join(Object[] arr, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(seperator + arr[i]);
		}
		if (sb.length() > 0)
			sb.deleteCharAt(0);
		return sb.toString();
	}
	
    /**
     * <p>Checks if a String is not empty ("") and not null and not "null".</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("null")    = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
	public static boolean isNotEmpty(String str){
		return !StringUtils.isEmpty(str);
	}
	
	
	/**
     * <p>Checks if a String is empty ("") , null or "null".</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty("null")    = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return org.apache.commons.lang.StringUtils.isEmpty(str) || NULL.equals(str);
	}
}

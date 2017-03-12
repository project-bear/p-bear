package info.bear.utils.http;

import org.apache.commons.lang.StringUtils;

/**
 * 验证URL工具类
 * 
 * @author zhouchenguang
 */
public class VerifyUrlUtils {
	
	private static final String HTTP_PROTOCOL_HEADER="http://";
	
	/**
	 * 验证URL
	 * eg: 
	 * wx.hotwind.net/wxhd  --> http://wx.hotwind.net/wxhd
	 * wx.hotwind.net/wxhd/ --> http://wx.hotwind.net/wxhd
	 * http://wx.hotwind.net/wxhd --> http://wx.hotwind.net/wxhd
	 * http://wx.hotwind.net/wxhd/ --> http://wx.hotwind.net/wxhd
	 */
	public static String verifyUrl(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}
		
		if(!url.toLowerCase().startsWith(HTTP_PROTOCOL_HEADER)){
			url = HTTP_PROTOCOL_HEADER + url;
		}
		
		if(url.endsWith("/")){
			url = url.substring(0, url.length()-1);
		}
		return url;
	}

}

package info.bear.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.bear.utils.json.JsonUtils;

/**
 * http请求工具类
 * @author zhouchenguang
 */
public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * http post请求
	 * @param url
	 * @return
	 */
	public static String post(String url){
		if(LOG.isDebugEnabled()){
			LOG.debug("http post request url is {}", url);
		}
		HttpPost httpPost = new HttpPost(url);
		return post(httpPost, null);
	}

	/**
	 * http post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, Object> params){
		if(LOG.isDebugEnabled()){
			LOG.debug("http post request url is {}", url);
		}
		HttpPost httpPost = new HttpPost(url);
		return post(httpPost, params);
	}
	
	/**
	 * http post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(URI uri){
		HttpPost httpPost = new HttpPost(uri);
		return post(httpPost, null);
	}
	
	/**
	 * http post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(URI uri, Map<String, Object> params){
		HttpPost httpPost = new HttpPost(uri);
		return post(httpPost, params);
	}
	
	/**
	 * http post请求
	 * @param url
	 * @param params
	 * @return
	 */
	private static String post(HttpPost httpPost, Map<String, Object> params){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResopnse = null;
		try {
			httpClient =  HttpClients.createDefault();
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
			if(params != null && !params.isEmpty()){
				for(Map.Entry<String, Object> entry : params.entrySet()){	
					formParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			
			if(!formParams.isEmpty()){
				UrlEncodedFormEntity uriEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
				httpPost.setEntity(uriEntity);
			}
			httpResopnse = httpClient.execute(httpPost);
			HttpEntity entity = httpResopnse.getEntity();
			return EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
			if(httpResopnse != null){
				try {
					httpResopnse.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 通过Post请求发送xml数据
	 * @param url
	 * @param xml
	 * @return
	 */
	public static String sendXmlByPost(String url, String xml){
		HttpPost httpPost = new HttpPost(url);
		return postByStream(httpPost, xml, ContentType.create(ContentType.APPLICATION_XML.getMimeType(), Consts.UTF_8));
	}

	/**
	 * 通过Post请求发送json数据
	 * @param url
	 * @param json
	 * @return
	 */
	public static String sendJsonByPost(String url, Map<String, Object> params){
		return sendJsonByPost(url, JsonUtils.writeValueAsString(params));
	}
	
	/**
	 * 通过Post请求发送json数据
	 * @param url
	 * @param json
	 * @return
	 */
	public static String sendJsonByPost(String url, String json){
		HttpPost httpPost = new HttpPost(url);
		return postByStream(httpPost, json, ContentType.APPLICATION_JSON);
	}

	/**
	 * http post请求
	 * 	直接以流的形式写到服务端
	 * @param url
	 * @param params
	 * @return
	 */
	private static String postByStream(HttpPost httpPost, String requestBody, ContentType contentType){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResopnse = null;
		try {
			httpClient =  HttpClients.createDefault();
			
			if(StringUtils.isNotBlank(requestBody)){
				StringEntity stringEntity = new StringEntity(requestBody, contentType);	
				httpPost.setEntity(stringEntity);
			}
			httpResopnse = httpClient.execute(httpPost);
			HttpEntity entity = httpResopnse.getEntity();
			return EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
			if(httpResopnse != null){
				try {
					httpResopnse.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * http get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url){
		HttpGet httpGet = new HttpGet(url);
		return get(httpGet);
	}
	

	/**
	 * http get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, Object> params){
		if(params != null && !params.isEmpty()){
			StringBuffer paramSb = new StringBuffer("?");
			for(Map.Entry<String, Object> entry : params.entrySet()){
				paramSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			
			String param = paramSb.toString();
			param = param.substring(0, param.length()-1);
			url += param;
		}
		HttpGet httpGet = new HttpGet(url);
		return get(httpGet);
	}
	
	/**
	 * http get请求
	 * @param url
	 * @return
	 */
	public static String get(URI uri){
		HttpGet httpGet = new HttpGet(uri);
		return get(httpGet);
	}
	
	
	/**
	 * http get请求
	 * @param url
	 * @return
	 */
	private static String get(HttpGet httpGet){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResopnse = null;
		try {
			httpClient = HttpClients.createDefault();
			httpResopnse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResopnse.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} finally{
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
			if(httpResopnse != null){
				try {
					httpResopnse.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 前台调用后台接口
	 * @param requestName
	 * @param params
	 * @return
	 */
	public static String connByMobile(String host, String requestName, Map<String, Object> params) {
		String url = VerifyUrlUtils.verifyUrl(host) + "/o2oOrder/" + requestName + ".do";
		return connection(url, params);
	}

	public static String connection(String url, Map<String, Object> params) {
		java.io.OutputStream stream = null;
		BufferedReader br = null;
		try {
			String contentToPost = JsonUtils.writeValueAsString(params);
			URLConnection connection = new URL(url).openConnection();
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Length", "" + contentToPost.length());
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Cache-Control", "no-cache");
			stream = connection.getOutputStream();
			stream.write(contentToPost.getBytes("UTF-8"));
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String str = br.readLine();
			while (str != null) {
				sb.append(str);
				str = br.readLine();
			}
			return sb.toString();
		} catch (IOException e1) {
			LOG.error(e1.getMessage());
			e1.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

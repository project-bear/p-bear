package info.bear.core;

import java.io.Serializable;

/**
 * 服务返回对象
 * 
 * @author zhouchenguang
 * @param <T>
 */
public class ServiceResultHandler<T> implements Serializable{

	/**
	 * 服务返回状态
	 */
	private ServiceStatusCode statusCode;

	/**
	 * 服务返回信息
	 */
	private String message;
	
	/**
	 * 服务异常信息
	 */
	private Exception exception;

	/**
	 * 服务返回数据
	 */
	private T data;
	
	public ServiceResultHandler() {
		
	}

	public ServiceResultHandler(ServiceStatusCode statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public ServiceResultHandler(Exception exception) {
		this.exception = exception;
	}

	public ServiceResultHandler(ServiceStatusCode statusCode, T data) {
		this.statusCode = statusCode;
		this.data = data;
	}

	public ServiceStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(ServiceStatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}

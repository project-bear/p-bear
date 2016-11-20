package info.bear.core;

/**
 * 服务返回的StatusCode
 * 
 * @author zhouchenguang
 */
public enum ServiceStatusCode {
	
	OK(200, "成功"), 
	FORBIDDEN(403, "权限不足"), 
	NOTFOUND(404, "找不到资源"), 
	TIMEOUT(406, "请求超时"), 
	EXCEPTION(500, "内部异常");
	
	private Integer code;
	
	private String desc;

	private ServiceStatusCode(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}

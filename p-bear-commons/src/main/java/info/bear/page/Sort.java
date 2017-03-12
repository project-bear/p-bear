package info.bear.page;

import java.io.Serializable;

/**
 * 排序基类
 * 
 * @author zhouchenguang
 */
public class Sort implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2696006163996739363L;

	
	public enum SortEnum{
		/**
		 * 倒序
		 */
		DESC,
		
		/**
		 * 正序
		 */
		ASC;
	}
	
	/**
	 * 排序字段
	 */
	private String field;
	
	private SortEnum type;

	public Sort() {
		
	}
	
	public Sort(String field) {
		this.field = field;
		this.type = SortEnum.ASC;
	}

	public Sort(String field, SortEnum type) {
		this.field = field;
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public SortEnum getType() {
		return type;
	}

	public void setType(SortEnum type) {
		this.type = type;
	}
}

package info.bear.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页基本类
 * 
 * @author zhouchenguang
 */
public class Pagination<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4128489683978709225L;
	
	/**
	 * 默认每页显示10条数据
	 */
	private static final long DEFAULT_PAGE_SIZE = 10;

	/**
	 * 每页显示多少条数据
	 */
	private long pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 分页数
	 */
	private long pageNo;
	
	/**
	 * 总记录数
	 */
	private long totalCount;

	/**
	 * 总页数
	 */
	private long totalPage;
	
	/**
	 * 分页数据
	 */
	private List<T> result;
	
	public Pagination() {
		this(DEFAULT_PAGE_SIZE, 0, 0);
	}

	public Pagination(long pageNo, long totalCount) {
		this(DEFAULT_PAGE_SIZE, pageNo, totalCount);
	}

	public Pagination(long pageSize, long pageNo, long totalCount) {
		this(pageSize, pageNo, totalCount, new ArrayList<T>());
	}

	public Pagination(long pageSize, long pageNo, long totalCount,  List<T> result) {
		setPageSize(pageSize);
		setPageNo(pageNo);
		setResult(result);
		setTotalCount(totalCount);
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		if(totalCount % pageSize == 0){
			totalPage = totalCount / pageSize;
		}else{
			totalPage = (totalCount / pageSize) + 1;
		}
		setTotalPage(totalPage);
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}

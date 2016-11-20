package info.bear.page;

import java.io.Serializable;

/**
 * 分页基类
 * 
 * @author zhouchenguang
 */
public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2646290475475946078L;

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

	public Page() {
		this(1, DEFAULT_PAGE_SIZE);
	}

	public Page(long pageNo) {
		this(pageNo, DEFAULT_PAGE_SIZE);
	}

	public Page(long pageNo, long pageSize) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}
	
	/**
	 * 当前分页从第几个数开始
	 * @return
	 */
	public long getStart() {
		return (pageNo - 1) * pageSize;
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
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
}

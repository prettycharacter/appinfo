package cn.appsys.utils;

public class PageBean {
	private int currentPageNo;// 当前页
	
	private int pageSize;// 页容量
	
	private int totalCount;// 总数量
	
	private int totalPage;// 总页数
	

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		if (currentPageNo > 0) {
			this.currentPageNo = currentPageNo;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			this.setTotalPageByRs();
		}
	}

	public int getTotalPage() {
		return totalPage;
	}


	private void setTotalPageByRs() {
		if (this.totalCount % this.pageSize == 0) {
			this.totalPage = this.totalCount / this.pageSize;
		} else if (this.totalCount % this.pageSize > 0) {
			this.totalPage = (this.totalCount / this.pageSize) + 1;
		} else {
			this.totalPage = 0;
		}
	}
}

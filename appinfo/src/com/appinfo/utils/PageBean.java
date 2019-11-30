package com.appinfo.utils;

import java.util.List;


/**
 * 分页工具类
 * @author Charles
 *
 * @param <T>
 */
public class PageBean<T> {

	private int currentPageNo; // 当前页
	private int pageSize; // 每页显示多少条
	private int totalCount; // 总记录数  select count(1) from news_detail;
	private int totalPage; // 总页数 
	private List<T> list; // 每页显示的数据集合
	private int startIndex;
	
	public int getStartIndex() {
		return (this.getCurrentPageNo() - 1) * this.getPageSize();
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	// 实现在设置总记录数的同时计算总页数
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if(totalCount > 0) {
			this.setTotalPage(this.getTotalCount() % this.getPageSize() == 0 
									? this.getTotalCount() / this.getPageSize()
											: (this.getTotalCount() / this.getPageSize())+1);
		}
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}

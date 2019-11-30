package com.appinfo.service.appinfo;

import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.param.QueryAppInfoParam;
import com.appinfo.utils.PageBean;

public interface AppInfoService {

	/**
	 * 分页查询App基础列表
	 * @param pageBean
	 * @param appInfoParam
	 */
	void listAppInfo(PageBean<AppInfo> pageBean,QueryAppInfoParam appInfoParam);

	/**
	 * 根据id查询相应id的app信息
	 * @param id
	 * @return
	 */
	AppInfo getAppInfoById(Integer id);

	/**
	 * 判断APKName是否重复
	 * @param aPKName
	 * @return
	 */
	boolean checkAPKNameIsExists(String APKName);

	/**
	 * 添加app的信息的方法
	 * @param appInfo
	 * @return
	 */
	boolean add(AppInfo appInfo);

	/**
	 * 修改app信息的方法
	 * @param appInfo
	 * @return
	 */
	boolean modify(AppInfo appInfo);

	/**
	 * 根据app的id删除app的信息
	 * @param id
	 * @return
	 */
	boolean deleteAppById(Integer id)throws Exception;

	/**
	 * 删除本地logo图片
	 * @param parseInt
	 * @return
	 */
	boolean deleteAppLogoPic(int id);

	/**
	 * 根据APP的i的修改app的状态
	 * @param appInfo
	 * @return
	 */
	boolean appUpdateSaleStatusByAppId(AppInfo appInfo)throws Exception;

	/**
	 * 根据app的id修改状态
	 * @param status
	 * @param id
	 * @return
	 */
	int updateAppManager(Integer status, Integer id);
}

package com.appinfo.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.param.QueryAppInfoParam;

public interface AppInfoMapper {

	/**
	 * 根据条件查询总记录数
	 * @param appInfoParam
	 * @return
	 */
	int countAppInfo(QueryAppInfoParam appInfoParam);

	/**
	 * 根据条件查询基础信息列表(分页)
	 * @param appInfoParam
	 * @return
	 */
	List<AppInfo> listAppInfo(QueryAppInfoParam appInfoParam);

	/**
	 * 根据id查询app的基本信息
	 * @param id
	 * @return
	 */
	AppInfo getAppInfoById(@Param("id")Integer id);

	/**
	 * 根据APKName查询app的信息
	 * @param APKName
	 * @return
	 */
	AppInfo checkAppInfoByAPKName(@Param("APKName")String APKName);

	/**
	 * 插入app的信息
	 * @param appInfo
	 * @return
	 */
	int add(AppInfo appInfo);

	/**修改APP信息的方法
	 * @param appInfo
	 * @return
	 */
	int modify(AppInfo appInfo);

	/**
	 * 在添加新版本的时候更新当前版本id
	 * @param versionId
	 * @param appId
	 * @return
	 */
	int updateVersionId(@Param(value="versionId")Integer versionId,@Param(value="id") Integer appId);

	/**
	 * 根据app的id删除app的相关信息
	 * @param id
	 * @return
	 */
	int deleteAppInfoById(Integer id);

	/**
	 * 根据id删除本地logo图片
	 * @param id
	 * @return
	 */
	int deleteAppLogoPic(int id);

	/**
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	int updateAppMananger(@Param(value="status")Integer status, @Param(value="id")Integer id);
	
	
	
	
}

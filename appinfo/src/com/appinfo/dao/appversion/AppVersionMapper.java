package com.appinfo.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppVersion;

public interface AppVersionMapper {

	/**
	 * 根据app的id查询相关的版本信息
	 * @param id
	 * @return
	 */
	List<AppVersion> listAppVersionByAppId(@Param("id")Integer id);

	/**
	 * 增加app版本信息
	 * @param appVersion
	 * @return
	 */
	int add(AppVersion appVersion);

	/**根据id获取app版本信息
	 * @param id
	 * @return
	 */
	AppVersion getAppVersionById(@Param("id")int id);

	/**
	 * 修改app版本信息
	 * @param appVersion
	 * @return
	 */
	int modify(AppVersion appVersion);

	/**
	 * 根据app的id查询版本记录数
	 * @param id
	 * @return
	 */
	int getVersionCountByAppId(@Param("appId")Integer id);

	/**
	 * 根据APP的id删除相关的版本信息
	 * @param id
	 */
	int deleteVersionByAppId(@Param("appId")Integer id);

	/**
	 * 根据版本id清空apk文件的相关信息
	 * @param id
	 * @return
	 */
	int deleteApkFile(int id);

}

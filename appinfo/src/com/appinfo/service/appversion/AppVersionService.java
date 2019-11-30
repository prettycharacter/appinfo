package com.appinfo.service.appversion;

import java.util.List;

import com.appinfo.pojo.AppVersion;

public interface AppVersionService {

	/**
	 * 根据appid查询历史版本信息
	 * @param id
	 * @return
	 */
	List<AppVersion> listAppversionByAppId(Integer id);

	/**
	 * 添加app版本信息
	 * @param appVersion
	 * @return
	 */
	boolean appVersionAddSave(AppVersion appVersion);

	/**
	 * 根据版本id获取版本信息
	 * @param parseInt
	 * @return
	 */
	AppVersion getAppVersionById(int id);

	/**
	 * 修改app版本信息
	 * @param appVersion
	 * @return
	 */
	boolean modify(AppVersion appVersion);

	/**
	 * 根据版本id删除本地APK文件
	 * @param id
	 * @return
	 */
	boolean deleteApkFile(int id);

	

}

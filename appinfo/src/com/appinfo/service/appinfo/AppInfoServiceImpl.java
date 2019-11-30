package com.appinfo.service.appinfo;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.appinfo.AppInfoMapper;
import com.appinfo.dao.appversion.AppVersionMapper;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.param.QueryAppInfoParam;
import com.appinfo.service.appinfo.AppInfoService;
import com.appinfo.utils.PageBean;
@Service
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;
	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public boolean deleteAppLogoPic(int id) {
		boolean flag = false;
		if(appInfoMapper.deleteAppLogoPic(id) > 0){
			flag = true;
		}
		return flag;
	}

	
	
	@Override
	public boolean deleteAppById(Integer id) throws Exception {
		boolean flag = false;
		int versionCount = appVersionMapper.getVersionCountByAppId(id);
		List<AppVersion> appVersionList = null;
		if(versionCount > 0){//1 先删版本信息
			//<1> 删除上传的apk文件
			appVersionList = appVersionMapper.listAppVersionByAppId(id);
			for(AppVersion appVersion:appVersionList){
				if(appVersion.getApkLocPath() != null && !appVersion.getApkLocPath().equals("")){
					File file = new File(appVersion.getApkLocPath());
					if(file.exists()){
						if(!file.delete()) {
							throw new Exception();
						}
					}
				}
			}			
			//<2> 删除app_version表数据
			appVersionMapper.deleteVersionByAppId(id);
		}
		//2 再删app基础信息
		//<1> 删除上传的logo图片
		AppInfo appInfo = appInfoMapper.getAppInfoById(id);
		if(appInfo.getLogoLocPath() != null && !appInfo.getLogoLocPath().equals("")){
			File file = new File(appInfo.getLogoLocPath());
			if(file.exists()){
				if(!file.delete()) {
					throw new Exception();
				}	
			}
		}
		//<2> 删除app_info表数据
		if(appInfoMapper.deleteAppInfoById(id) > 0){
			flag = true;
		}
		return flag;
	}
	
	
	@Override
	public boolean modify(AppInfo appInfo) {
		boolean flag = false;
		if(appInfoMapper.modify(appInfo) > 0){
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean add(AppInfo appInfo) {
		boolean flag = false;
		if(appInfoMapper.add(appInfo) > 0){
			flag = true;
		}
		return flag;
	}

	
	@Override
	public boolean checkAPKNameIsExists(String APKName) {
		if(appInfoMapper.checkAppInfoByAPKName(APKName) == null) {
			return true;
		}else {
			return false;
		}
	}

	
	@Override
	public void listAppInfo(PageBean<AppInfo> pageBean,QueryAppInfoParam appInfoParam) {
		pageBean.setTotalCount(appInfoMapper.countAppInfo(appInfoParam));
		pageBean.setList(appInfoMapper.listAppInfo(appInfoParam));
	}

	@Override
	public AppInfo getAppInfoById(Integer id) {
		
		return appInfoMapper.getAppInfoById(id);
	}



	@Override
	public boolean appUpdateSaleStatusByAppId(AppInfo appInfo) throws Exception {
		AppInfo appInfomation = appInfoMapper.getAppInfoById(appInfo.getId());
		Integer operator = appInfo.getModifyBy();
		if(operator <0  || appInfo.getId() <0) {
			
		}
		if(null == 	appInfomation) {
			return false;
		}else {
			switch(appInfomation.getStatus()) {
			case 2:
				onSale(appInfomation,operator,4,2);
				break;
			case 5 :
				onSale(appInfomation,operator,4,2);
				break;	
			case 4 :
				offSale(appInfomation,operator,5);
				break;
			default:
				return false;
			}
		}
		return true;
	}
	/**
	 * 上架
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @param versionStatus
	 * @throws Exception
	 */
	private void onSale(AppInfo appInfo,Integer operator,Integer appInfStatus,Integer versionStatus) throws Exception{
		offSale(appInfo,operator,appInfStatus);
		setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
	}
	
	
	/**
	 *下架
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @return
	 * @throws Exception
	 */
	private boolean offSale(AppInfo appInfo,Integer operator,Integer appInfStatus) throws Exception{
		AppInfo _appInfo = new AppInfo();
		_appInfo.setId(appInfo.getId());
		_appInfo.setStatus(appInfStatus);
		_appInfo.setModifyBy(operator);
		_appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
		appInfoMapper.modify(_appInfo);
		return true;
	}
	
	/**
	 * set sale method to on or off
	 * @param appInfo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private boolean setSaleSwitchToAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) throws Exception{
		AppVersion appVersion = new AppVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date(System.currentTimeMillis()));
		appVersionMapper.modify(appVersion);
		return false;
	}



	@Override
	public int updateAppManager(Integer status, Integer id) {
		
		return appInfoMapper.updateAppMananger(status ,id);
	}
	

	
	


	


	
	
}

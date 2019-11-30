package com.appinfo.service.appversion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.appinfo.AppInfoMapper;
import com.appinfo.dao.appversion.AppVersionMapper;
import com.appinfo.pojo.AppVersion;
@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;
	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Override
	public AppVersion getAppVersionById(int id) {
		return appVersionMapper.getAppVersionById(id);
	}
	
	@Override
	public List<AppVersion> listAppversionByAppId(Integer id) {
		
		return appVersionMapper.listAppVersionByAppId(id);
	}
	@Override
	public boolean appVersionAddSave(AppVersion appVersion) {
		boolean flag = false;
		Integer versionId = null;
		if(appVersionMapper.add(appVersion) > 0){
			versionId = appVersion.getId();
			flag = true;
		}
		if(appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean modify(AppVersion appVersion) {
		boolean flag = false;
		if(appVersionMapper.modify(appVersion) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean deleteApkFile(int id) {
		boolean flag = false;
		if(appVersionMapper.deleteApkFile(id) > 0) {
			flag = true;
		}
		return flag;
	}
	

}

package cn.appsys.service.develop.appversion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;
	@Resource
	private AppInfoMapper appInfoMapper;
	@Override
	public List<AppVersion> getAppVersionList(int appId) {
		List<AppVersion> appVersionList=null;
		appVersionList=appVersionMapper.getAppVersionList(appId);
		return appVersionList;
	}
	@Override
	public boolean addAppVersion(AppVersion appVersion) {
		boolean flag=false;
		Integer versionId=null;
		if(appVersionMapper.addAppVersion(appVersion)>0) {
			flag=true;
			versionId=appVersion.getId();
			if(appInfoMapper.updateVersionId(versionId,appVersion.getAppId())>0) {
				flag = true;
			}else {
				flag = false;
			}
		}
		return flag;
	}

}

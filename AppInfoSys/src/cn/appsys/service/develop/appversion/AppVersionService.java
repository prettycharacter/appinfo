package cn.appsys.service.develop.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {

	List<AppVersion> getAppVersionList(int appId);

	boolean addAppVersion(AppVersion appVersion);

}

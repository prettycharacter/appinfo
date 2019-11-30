package cn.appsys.service.develop.appinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper appInfoMapper;

	// 获取app信息列表
	@Override
	public List<AppInfo> getAppInfoList(Integer devId, String softwareName, Integer status, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer pageIndex, Integer pageSize)
			throws Exception {
		List<AppInfo> appInfoList = null;
		try {
			appInfoList = appInfoMapper.getAppInfoList(devId, softwareName, status, flatformId, categoryLevel1,
					categoryLevel2, categoryLevel3, (pageIndex-1)*pageSize, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return appInfoList;
	}

	// 获取app信息的数量
	@Override
	public int getAppInCount(Integer devId, String softwareName, Integer status, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer pageIndex, Integer pageSize)
			throws Exception {
		int count = 0;
		try {
			count = appInfoMapper.getAppInCount(devId, softwareName, status, flatformId, categoryLevel1, categoryLevel2,
					categoryLevel3, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}
	//查看软件名称是否重复
	@Override
	public boolean softwareNameisExist(String softwareName) throws Exception {
		boolean flag;
		int count=appInfoMapper.softwareNameisExist(softwareName);
		if(count>0) {
			flag=false;
		}else {
			flag=true;
		}
		return flag;
	}
	//查看APKName是否重复
	@Override
	public boolean APKNameisExist(String APKName) throws Exception {
		boolean flag;
		int count=appInfoMapper.APKNameisExist(APKName);
		if(count>0) {
			flag=false;
		}else {
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean addAppInfo(AppInfo appInfo)throws Exception {
		boolean flag=false;
		int row=appInfoMapper.addAppInfo(appInfo);
		if(row>0) {
			flag=true;
		}
		return flag;
	}

	

	@Override
	public boolean ModifyAppInfo(AppInfo appInfo)throws Exception {
		boolean flag=false;
		int row=appInfoMapper.modifyAppInfo(appInfo);
		if(row>0) {
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean deleteAppInfo(String id)throws Exception {
		boolean flag=false;
		int row=appInfoMapper.deleteAppInfo(id);
		if(row>0) {
			flag=true;
		}
		return flag;
	}

	@Override
	public AppInfo getAppInfoById(String id)throws Exception {
		AppInfo appInfo=null;
		appInfo=appInfoMapper.getAppInfoById(id);
		return appInfo;
	}
}

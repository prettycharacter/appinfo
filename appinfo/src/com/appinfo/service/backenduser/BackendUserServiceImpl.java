package com.appinfo.service.backenduser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.backenduser.BackendUserMapper;
import com.appinfo.pojo.BackendUser;

import com.appinfo.utils.MD5;
@Service
public class BackendUserServiceImpl implements BackendUserService {
	@Resource
	private BackendUserMapper backendUserMapper;
	@Override
	public BackendUser login(String userCode, String userPassword) {
		BackendUser backendUser = backendUserMapper.getBackendUserByUserCode(userCode);
		if(null != backendUser) {
			if(!backendUser.getUserPassword().equals(userPassword)) { // 判定密码是否相同
				backendUser = null;
			}
		}
		return backendUser;
	}
	
}

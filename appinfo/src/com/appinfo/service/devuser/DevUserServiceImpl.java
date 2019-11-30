package com.appinfo.service.devuser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.devuser.DevUserMapper;
import com.appinfo.pojo.DevUser;
import com.appinfo.utils.MD5;

@Service
public class DevUserServiceImpl implements DevUserService {

	@Resource
	private DevUserMapper devUserMapper;
	
	@Override
	public DevUser login(String devCode, String devPassword) {
		DevUser devUser = devUserMapper.getDevUserByDevCode(devCode);
		if(null != devUser) {
			if(!devUser.getDevPassword().equals(devPassword)) { // 判定密码是否相同
				devUser = null;
			}
		}
		return devUser;
	}

}

package cn.appsys.service.develop.devuser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceImpl implements DevUserService{
	@Resource
	private DevUserMapper devUserMapper;
	@Override
	//登录验证
	public DevUser devLogin(String devCode,String password) throws Exception {
		DevUser devUser=devUserMapper.login(devCode);
		if(devUser!=null) {
			if(!devUser.getDevPassword().equals(password)) {
				devUser=null;
			}
		}
		return devUser;
	}
	
}

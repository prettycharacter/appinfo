package com.appinfo.service.devuser;

import com.appinfo.pojo.DevUser;

public interface DevUserService {

	/**
	 * 开发者登录
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	DevUser login(String devCode, String devPassword);

}

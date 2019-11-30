package com.appinfo.service.backenduser;

import com.appinfo.pojo.BackendUser;

public interface BackendUserService {
	
	  /**
	   * 管理用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	BackendUser login(String userCode, String userPassword) ;

}

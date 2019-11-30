package cn.appsys.service.develop.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	/**开发者登录验证方法
	 * @param devCode
	 * @return
	 * @throws Exception
	 */
	public DevUser devLogin(String devCode,String password)throws Exception;
}

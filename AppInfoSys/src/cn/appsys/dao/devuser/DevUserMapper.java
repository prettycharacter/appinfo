package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
    /**根据DevCode获得登录用户的信息。
     * @param devCode
     * @return
     * @throws Exception
     */
    public DevUser login(@Param("devCode")String devCode)throws Exception;
}

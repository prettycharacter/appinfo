package com.appinfo.dao.backenduser;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.BackendUser;

public interface BackendUserMapper {

	BackendUser getBackendUserByUserCode(@Param("userCode")String userCode);

}

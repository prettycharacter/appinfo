package cn.appsys.service.develop.appcategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
    //根据pid查询等级列表
	List<AppCategory> getCategoryListByPid(Integer pid)throws Exception;

}

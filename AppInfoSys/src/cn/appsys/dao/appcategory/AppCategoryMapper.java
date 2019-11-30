package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	//根据父id查询新闻列表
	List<AppCategory> getCategoryListByPid(@Param("parentId")Integer pid);

}

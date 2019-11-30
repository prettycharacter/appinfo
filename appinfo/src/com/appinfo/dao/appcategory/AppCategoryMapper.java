package com.appinfo.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.AppCategory;

public interface AppCategoryMapper {

	/**
	 * 根据parentId 查询分类列表信息
	 * @param parentId
	 * @return
	 */
	List<AppCategory> listAppCategoryByParentId(@Param("parentId")Integer parentId);

}

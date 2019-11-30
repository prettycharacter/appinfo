package com.appinfo.service.appcategory;

import java.util.List;

import com.appinfo.pojo.AppCategory;

public interface AppCategoryService {

	/**
	 * 根据父级ID查询分类列表
	 * @param object
	 * @return
	 */
	List<AppCategory> listAppCategoryByParentId(Integer parentId);

}

package com.appinfo.service.appcategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.appcategory.AppCategoryMapper;
import com.appinfo.pojo.AppCategory;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {

	@Resource
	private AppCategoryMapper appCategoryMapper;
	
	@Override
	public List<AppCategory> listAppCategoryByParentId(Integer parentId) {
		return appCategoryMapper.listAppCategoryByParentId(parentId);
	}

}

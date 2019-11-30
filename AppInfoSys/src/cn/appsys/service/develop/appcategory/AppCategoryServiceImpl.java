package cn.appsys.service.develop.appcategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;
	@Override
	public List<AppCategory> getCategoryListByPid(Integer pid)throws Exception {
		List<AppCategory> appCategoryList=null;
		try {
			appCategoryList = appCategoryMapper.getCategoryListByPid(pid);	
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return appCategoryList;
	}

}

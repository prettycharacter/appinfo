package com.appinfo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.appinfo.pojo.AppCategory;
import com.appinfo.service.appcategory.AppCategoryService;

@Controller
@RequestMapping("/dev/appcategory")
public class AppCategoryController {

	@Resource
	private AppCategoryService appCategoryService;
	
	/**
	 * 三级联动查询子级分类列表
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/categorylevellist/{parentId}")
	public String doCategoryLeveList(@PathVariable Integer parentId) {
		List<AppCategory> categoryList = appCategoryService.listAppCategoryByParentId(parentId);
		return JSON.toJSONString(categoryList);
	}
}

package com.appinfo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.pojo.param.QueryAppInfoParam;
import com.appinfo.service.appcategory.AppCategoryService;
import com.appinfo.service.appinfo.AppInfoService;
import com.appinfo.service.appversion.AppVersionService;
import com.appinfo.service.datadictionary.DataDictionaryService;
import com.appinfo.utils.PageBean;
import com.appinfo.utils.constant.DefaultPageConstant;

@Controller
@RequestMapping(value="/manager/backend/app")
public class ManagerController {
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;
	
	/**
	 * 提交审核
	 * @param appInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checksave",method=RequestMethod.POST)
	public String doCheckSave(@ModelAttribute AppInfo appInfo,HttpServletRequest request) {
		try {
			int i = appInfoService.updateAppManager(appInfo.getStatus(),appInfo.getId());
			if(i == 0) {
				request.setAttribute("error", "审核失败！请重试！");
				return "/check";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/500.jsp";
		}
		return "redirect:/manager/backend/app/list";
	}
	/**
	 * 审核查询信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.GET)
	public String doCheck(HttpServletRequest request) {
		String said = request.getParameter("aid");
		Integer aid = Integer.valueOf(said);
		String svid = request.getParameter("vid");
		Integer vid = Integer.valueOf(svid);
		try {
			AppInfo appInfo = appInfoService.getAppInfoById(aid);
			request.setAttribute("appInfo", appInfo);
			
			AppVersion appVersion = appVersionService.getAppVersionById(vid);
			request.setAttribute("appVersion", appVersion);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/500.jsp";
		}
		return "backend/appcheck";
	}

	/**
	 * 系统管理页面的加载App审核列表
	 * @param appInfoParam
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method= {RequestMethod.GET,RequestMethod.POST})
	public String doBackendList(@ModelAttribute QueryAppInfoParam appInfoParam,Model model) {
		appInfoParam.setPageIndex(appInfoParam.getPageIndex() == null ? 1 : appInfoParam.getPageIndex());
		appInfoParam.setPageSize(DefaultPageConstant.PAGE_SIZE);
		
		PageBean<AppInfo> pageBean = new PageBean<AppInfo>();
		pageBean.setCurrentPageNo(appInfoParam.getPageIndex());
		pageBean.setPageSize(appInfoParam.getPageSize());
		appInfoParam.setStartIndex(pageBean.getStartIndex());
		appInfoService.listAppInfo(pageBean,appInfoParam);
		
		// 查询App状态列表  
		List<DataDictionary> statusList = dataDictionaryService.listDataDictionaryByTypeCode("APP_STATUS");
		// 查询App平台列表
		List<DataDictionary> flatFormList = dataDictionaryService.listDataDictionaryByTypeCode("APP_FLATFORM");
		
		// 三级联动
		// 基础查询1级分类
		List<AppCategory> categoryLevel1List = appCategoryService.listAppCategoryByParentId(null);
		// 如果有1级分类ID传递过来  应该顺便查询2级分类列表
		if(appInfoParam.getQueryCategoryLevel1() != null) {
			List<AppCategory> categoryLevel2List = appCategoryService.listAppCategoryByParentId(appInfoParam.getQueryCategoryLevel1());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(appInfoParam.getQueryCategoryLevel2() != null) {
			List<AppCategory> categoryLevel3List = appCategoryService.listAppCategoryByParentId(appInfoParam.getQueryCategoryLevel2());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		// 回显查询条件
		model.addAttribute("appInfoParam",appInfoParam);
		return "backend/applist";
	}
}

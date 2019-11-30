package com.appinfo.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.appinfo.pojo.AppCategory;
import com.appinfo.pojo.AppInfo;
import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.DataDictionary;
import com.appinfo.pojo.DevUser;
import com.appinfo.pojo.param.QueryAppInfoParam;
import com.appinfo.service.appcategory.AppCategoryService;
import com.appinfo.service.appinfo.AppInfoService;
import com.appinfo.service.appversion.AppVersionService;
import com.appinfo.service.datadictionary.DataDictionaryService;
import com.appinfo.utils.PageBean;
import com.appinfo.utils.constant.Constants;
import com.appinfo.utils.constant.DefaultPageConstant;
import com.appinfo.utils.EmptyUtils;

/**
 * appinfo控制器
 * @author Rare
 *
 */
@Controller
@RequestMapping("/dev/app")
public class AppInfoController {

	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;
	
	
	
	
	
	@RequestMapping(value ="/sale/{appId}",method = RequestMethod.PUT)
	@ResponseBody
	public String sale (@PathVariable Integer appId,HttpSession session) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("errorCode", "0");
		if(appId > 0 ) {
			try {
				DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
				AppInfo appInfo = new AppInfo();
				appInfo.setId(appId);
				appInfo.setModifyBy(devUser.getId());
				if(appInfoService.appUpdateSaleStatusByAppId(appInfo)) {
					resultMap.put("resultMsg", "success");
				}else {
					resultMap.put("resultMsg", "failed");
				}
				
			}catch(Exception e){
				resultMap.put("errorCode", "exception000001");
				
			}
		}else {
			resultMap.put("errorCode", "param000001");
		}
		
		return JSON.toJSONString(resultMap);
	}
	
	
	
	/**
	 * 根据app的id删除相关的文件
	 * @param flag
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delfile" , method = RequestMethod.GET)
	@ResponseBody
	public String deleteFile(@RequestParam(value="flag",required=false) String flag,
							@RequestParam(value="id",required=false) String id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		String fileLocPath = null;
		if(flag == null || flag.equals("") ||id == null || id.equals("")){
				resultMap.put("result", "failed");
		}else if(flag.equals("logo")){
			try {
				//获取本地文件的路径地址
				fileLocPath = appInfoService.getAppInfoById(Integer.parseInt(id)).getLogoLocPath();
				//找到这个路径下的文件
				File file = new File(fileLocPath);
			    if(file.exists())
			    	//使用delete方法删除文件
			     if(file.delete()){
			    	 //如果删除了文件将数据库中保留的相关信息删除
					if(appInfoService.deleteAppLogoPic(Integer.parseInt(id))){//更新表
							resultMap.put("result", "success");
					 }
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(flag.equals("apk")){
				try {
					fileLocPath = (appVersionService.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
					File file = new File(fileLocPath);
				    if(file.exists())
				     if(file.delete()){//删除服务器存储的物理文件
							if(appVersionService.deleteApkFile(Integer.parseInt(id))){//更新表
								resultMap.put("result", "success");
							 }
				    }
				} catch (Exception e) {
					e.printStackTrace();
				}
		}		
		return JSON.toJSONString(resultMap);
	}
	
	
	
	/**
	 * 根据app的id删除app的相关信息，要考虑到</br>
	 * 相关的版本信息和本地保存的相关图片资源，和相关的</br>
	 * APK资源
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delapp/{id}" ,method = RequestMethod.GET)
	@ResponseBody
	public String delAppById(@PathVariable Integer id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(EmptyUtils.isEmpty(id)) {
			resultMap.put("delResult", "notexist");
		}else {
			try {
				if(appInfoService.deleteAppById(id)) {
					
					resultMap.put("delResult", "true");
				}else {
					resultMap.put("delResult", "false");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return JSON.toJSONString(resultMap);
	}
		
	
	
	
	
	
	/**
	 * 保存修改后的appInfo
	 * @param appInfo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String modifySave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
							@RequestParam(value="attach",required= false) MultipartFile attach){		
		String logoPicPath =  null;
		String logoLocPath =  null;
		String APKName = appInfo.getAPKName();
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 500k
            	 return "redirect:/dev/app/appinfomodify/"+appInfo.getId();
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = APKName + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/app/appinfomodify/"+appInfo.getId();
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
            }else{
            	return "redirect:/dev/app/appinfomodify/"+appInfo.getId();
						 
            }
		}
		appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		try {
			if(appInfoService.modify(appInfo)){
				return "redirect:/dev/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfomodify";
	}
	
	
	/**
	 * 跳转到appinfo的修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appinfomodify/{id}",method = RequestMethod.GET)
	public String toModifyAppInfo(@PathVariable Integer id , Model model) {
		try {
			AppInfo appInfo = appInfoService.getAppInfoById(id);
			model.addAttribute("appInfo", appInfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfomodify";
	}
	
	/**
	 * APP新增信息的保存
	 * @param appInfo
	 * @param session
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value="/appinfoaddsave",method = RequestMethod.POST)
	public String addSave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required= false) MultipartFile attach) {
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 500k
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setStatus(1);
		try {
			if(appInfoService.add(appInfo)){
				return "redirect:/dev/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfoadd";
	}
	
	/**
	 * ajax验证APKName
	 * @param APKName
	 * @return
	 */
	@RequestMapping(value = "/apkIsExists",method = RequestMethod.POST)
	@ResponseBody
	public String doAPKNameIsExists(@RequestParam String APKName) {
		HashMap<String, Object> hashMap = new HashMap<>();
		try {
			if(EmptyUtils.isNotEmpty(APKName)) {
				if(appInfoService.checkAPKNameIsExists(APKName)) {
					hashMap.put("APKName", "noexist");
				}else {
					hashMap.put("APKName", "exist");
				}
			}else {
				hashMap.put("APKName", "empty");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * 动态加载添加APP信息页面的一级分类
	 * @return
	 */
	@RequestMapping(value = "/category1",method = RequestMethod.GET)
	@ResponseBody
	public String doCategory1() {
		List<AppCategory> categoryLevel1List = appCategoryService.listAppCategoryByParentId(null);
		return  JSON.toJSONString(categoryLevel1List);
	}
	
	
	/**
	 * 动态加载添加app信息页面的平台列表
	 * @return
	 */
	@RequestMapping(value = "/flatform",method = RequestMethod.GET)
	@ResponseBody
	public String doFlatform() {
		List<DataDictionary> flatFormList = dataDictionaryService.listDataDictionaryByTypeCode("APP_FLATFORM");
		return  JSON.toJSONString(flatFormList);
	}
	/**
	 * 跳转到添加app信息页面
	 * @return
	 */
	@RequestMapping(value="/appinfoadd",method = RequestMethod.GET)
	public String  toAppInfoAdd() {
		return "developer/appinfoadd";
	}
	
	@RequestMapping(value="/appview/{id}",method = RequestMethod.GET)
	public String doAppInfoByAppId(@PathVariable Integer id,
									Model model	) {
		try {
			AppInfo appInfo = appInfoService.getAppInfoById(id);
			List<AppVersion> appVersionList = appVersionService.listAppversionByAppId(id);
			model.addAttribute("appInfo", appInfo);
			model.addAttribute("appVersionList",appVersionList);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfoview";
	}
	/**
	 * 点击app维护时进行的方法
	 * @param appInfoParam
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String doAppList(@ModelAttribute QueryAppInfoParam appInfoParam,Model model) {
		
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
		return "developer/appinfolist";
	}
	
}

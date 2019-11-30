package cn.appsys.controller.develop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
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
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.develop.appcategory.AppCategoryService;
import cn.appsys.service.develop.appinfo.AppInfoService;
import cn.appsys.service.develop.appversion.AppVersionService;
import cn.appsys.service.develop.datadictionary.DataDictionaryService;
import cn.appsys.utils.Constants;
import cn.appsys.utils.PageBean;

@Controller
// 开发者系统管理路径
@RequestMapping("/dev/sys")
public class AppInfoController {
	private Logger logger = Logger.getLogger(AppInfoController.class);
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private DataDictionaryService dataDictionaryService;
	@Resource
	private AppCategoryService appcategoryService;
	@Resource 
	private AppVersionService appVersionService;
	
	//保存新增的APP信息
	public String addversionsave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
								@RequestParam(value="a_downloadLink",required=false)MultipartFile attach) {
		int sessionId=((DevUser)session.getAttribute(Constants.USER_SESSION)).getId();
		appVersion.setCreatedBy(sessionId);
		appVersion.setCreationDate(new Date());
		try {
		if(appVersionService.addAppVersion(appVersion)) {
			return "redirect:/dev/sys/applist";
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/dev/sys/addVersion/"+appVersion.getId();
	}
	//跳转到新增APP信息的页面
	@RequestMapping(value = "/addVersion/{appId}")
	public String addVersion(@PathVariable String appId,AppVersion appVersion,Model model) {
		appVersion.setAppId(Integer.parseInt(appId));
		List<AppVersion> appVersionList = null;
		try {
			appVersionList=appVersionService.getAppVersionList(Integer.parseInt(appId));
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appVersionList",appVersionList);
		model.addAttribute(appVersion);
		return "develop/addAppVersion";
	}
	@RequestMapping(value = "/applist")
	public String appList(Model model, HttpSession session,
			@RequestParam(value = "querysoftwareName", required = false) String querysoftwareName,
			@RequestParam(value = "querystatus", required = false) String querystatus,
			@RequestParam(value = "queryflatformId", required = false) String queryflatformId,
			@RequestParam(value = "querycategoryLevel1", required = false) String querycategoryLevel1,
			@RequestParam(value = "querycategoryLevel2", required = false) String querycategoryLevel2,
			@RequestParam(value = "querycategoryLevel3", required = false) String querycategoryLevel3,
			@RequestParam(value = "querypageIndex", required = false) String querypageIndex) {
		// 获取开发者用户的id值
		logger.info("================================querysoftwareName软件名称：" + querysoftwareName);
		logger.info("================================querystatus软件状态：" + querystatus);
		logger.info("================================queryflatformId所属平台：" + queryflatformId);
		logger.info("================================querycategoryLevel1一级标题：" + querycategoryLevel1);
		logger.info("================================querycategoryLevel2二级标题：" + querycategoryLevel2);
		logger.info("================================querycategoryLevel3三级标题：" + querycategoryLevel3);
		logger.info("================================querypageIndex传入页码：" + querypageIndex);
		Integer devId = ((DevUser) session.getAttribute(Constants.USER_SESSION)).getId();
		// 判断status等Integer类型的参数是否为空，不为空就进行强制类型的转换。
		Integer status = null;
		if (querystatus != null && querystatus != "") {
			status = Integer.parseInt(querystatus);
		}
		Integer flatformId = null;
		if (queryflatformId != null && queryflatformId != "") {
			flatformId = Integer.parseInt(queryflatformId);
		}
		Integer categoryLevel1 = null;
		if (querycategoryLevel1 != null && querycategoryLevel1 != "") {
			categoryLevel1 = Integer.parseInt(querycategoryLevel1);
		}
		Integer categoryLevel2 = null;
		if (querycategoryLevel2 != null && querycategoryLevel2 != "") {
			categoryLevel2 = Integer.parseInt(querycategoryLevel2);
		}
		Integer categoryLevel3 = null;
		if (querycategoryLevel3 != null && querycategoryLevel3 != "") {
			categoryLevel3 = Integer.parseInt(querycategoryLevel3);
		}
		Integer pageIndex = 1;
		if (querypageIndex != null && querypageIndex != "") {
			pageIndex = Integer.parseInt(querypageIndex);
		}
		Integer pageSize = Constants.PageSize;
		List<AppInfo> appInfoList = null;
		List<DataDictionary> dataStatusList = null;
		List<DataDictionary> dataFlatformList = null;
		List<AppCategory> appCategory1List = null;
		PageBean pageBean = new PageBean();
		try {
			// 获得APP列表信息
			appInfoList = appInfoService.getAppInfoList(devId, querysoftwareName, status, flatformId, categoryLevel1,
					categoryLevel2, categoryLevel3, pageIndex, pageSize);
			pageBean.setCurrentPageNo(pageIndex);
			pageBean.setPageSize(Constants.PageSize);
			int totalCount = appInfoService.getAppInCount(devId, querysoftwareName, status, flatformId, categoryLevel1,
					categoryLevel2, categoryLevel3, pageIndex, pageSize);
			pageBean.setTotalCount(totalCount);

			// 通过typeCode来获取status列表信息
			dataStatusList = dataDictionaryService.getDataDictionaryList("APP_STATUS");
			// 通过typeCode来获取flatformId列表
			dataFlatformList = dataDictionaryService.getDataDictionaryList("APP_FLATFORM");
			// 通过标题的父id来获取标题
			
			appCategory1List = appcategoryService.getCategoryListByPid(null);
			
			// 设置回显
			model.addAttribute("querysoftwareName", querysoftwareName);
			model.addAttribute("querystatus", status);
			model.addAttribute("queryflatformId", flatformId);
			model.addAttribute("querycategoryLevel1", categoryLevel1);
			model.addAttribute("querycategoryLevel2", categoryLevel2);
			model.addAttribute("querycategoryLevel3", categoryLevel3);
			model.addAttribute("pageBean", pageBean);
			// 查询出来的数据列表
			model.addAttribute("appInfoList", appInfoList);
			model.addAttribute("dataStatusList", dataStatusList);
			model.addAttribute("dataFlatformList", dataFlatformList);
			model.addAttribute("appCategory1List", appCategory1List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "develop/appinfolist";
	}

	@RequestMapping(value = "/categoryLevel", method = RequestMethod.GET)
	@ResponseBody
	public String categoryLevel2(@RequestParam("parentId") String parentId) {
		List<AppCategory> categoryList = new ArrayList<AppCategory>();
		Integer pid = null;
		if (parentId != null && parentId != "") {
			pid = Integer.parseInt(parentId);
		}
		try {
			categoryList = appcategoryService.getCategoryListByPid(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cjson = JSON.toJSONString(categoryList, SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.PrettyFormat);
		return cjson;
	}

	@RequestMapping("addappInfo")
	public String addAppInfo(@ModelAttribute("appInfo") AppInfo appInfo, HttpServletRequest request) {
		List<AppCategory> appCategory1List = null;
		List<DataDictionary> dataFlatformList = null;
		try {
			appCategory1List = appcategoryService.getCategoryListByPid(null);
			dataFlatformList = dataDictionaryService.getDataDictionaryList("APP_FLATFORM");
			request.setAttribute("appCategory1List", appCategory1List);
			request.setAttribute("dataFlatformList", dataFlatformList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "develop/addappinfo";
	}

	// 处理文件上传
	@RequestMapping(value="/doAddSave",method=RequestMethod.POST)
	public String doAddSave(AppInfo appInfo,
			 				HttpSession session,
			 				HttpServletRequest request,
			 				@RequestParam(value="attach",required=false)MultipartFile attach) {
		logger.info("==============================进入doAddSave方法===============================");
		
		String logoPicPath=null;
		String logoLocPath=null;
		if(!attach.isEmpty()) {
			System.out.println("进入文件处理功能");
			int fileSize=500000;
			List<String> format = Arrays.asList("jpg","png","gif","bmp");
			logoPicPath=attach.getOriginalFilename();
			String extensive=FilenameUtils.getExtension(logoPicPath);
			if(attach.getSize()>fileSize) {
				request.setAttribute("uploadError","文件超出500KB,请上传小于500KB的文件");
				return "develop/addappinfo";
			}else if(format.contains(extensive)) {
				String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadFile");
				String fileName=System.currentTimeMillis()+RandomUtils.nextInt(0,100000)+"_logoPicPath."+extensive;
				File saveFile=new File(path,fileName);
				if(!saveFile.exists()) {
					saveFile.mkdirs();
				}
				try {
					attach.transferTo(saveFile);
					logoLocPath="statics"+File.separator+"uploadFile"+File.separator+fileName;
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadError", "上传失败");
				    return "develop/addappinfo";
				}
				
			}else {
				request.setAttribute("uploadError","文件的 格式不正确，请上传，jpg,png,gif,bmp格式的图片");
				return "develop/addappinfo";
			}
		}
		int sessionId=((DevUser)session.getAttribute(Constants.USER_SESSION)).getId();
		appInfo.setCreatedBy(sessionId);
		appInfo.setDevId(sessionId);
		appInfo.setCreationDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		boolean result;
		try {
			result = appInfoService.addAppInfo(appInfo);
		if(result) {
			return "redirect:/dev/sys/applist";
		}else {
			request.setAttribute("uploadError", "服务器出错");
		    return "develop/addappinfo";
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("uploadError", "服务器出错");
		    return "develop/addappinfo";
		}
	}

	// 通过Ajax验证软件名称是否已经存在
	@RequestMapping("/softwareNameisExist")
	@ResponseBody
	public String softwareNameisExist(@RequestParam("softwareName") String softwareName) {
		System.out.println("软件名称为：" + softwareName);
		if (softwareName == null || "".equals(softwareName)) {
			return "{\"flag\":\"error\"}";
		}
		boolean flag = false;
		try {
			flag = appInfoService.softwareNameisExist(softwareName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag) {
			return "{\"flag\":\"true\"}";
		} else {
			return "{\"flag\":\"false\"}";
		}
	}

	// 通过ajax验证APK名称是否已经存在
	@RequestMapping("/APKNameisExist")
	@ResponseBody
	public String APKNameisExist(@RequestParam("APKName") String APKName) {
		if (APKName == null || "".equals(APKName)) {
			return "{\"flag\":\"error\"}";
		}
		boolean flag = false;
		try {
			flag = appInfoService.APKNameisExist(APKName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag) {
			return "{\"flag\":\"true\"}";
		} else {
			return "{\"flag\":\"false\"}";
		}
	}
	
	@RequestMapping(value="/appInfoModify/{id}",method=RequestMethod.GET)
	public String appInfoModify(@PathVariable String id,Model model) {
		logger.info("============================进入appInfoModify方法========================");
		AppInfo appInfo=null;
		List<DataDictionary> dataStatusList = null;
		List<AppCategory> appCategory1List = null;
		List<DataDictionary> dataFlatformList = null;
		try {
			appCategory1List = appcategoryService.getCategoryListByPid(null);
			dataStatusList = dataDictionaryService.getDataDictionaryList("APP_STATUS");
			dataFlatformList = dataDictionaryService.getDataDictionaryList("APP_FLATFORM");
			appInfo = appInfoService.getAppInfoById(id);
			
			model.addAttribute("appCategory1List",appCategory1List);
			model.addAttribute("dataStatusList", dataStatusList);
			model.addAttribute("dataFlatformList",dataFlatformList);
			model.addAttribute("appInfo",appInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return "develop/appInfoModify";
		}
		return "develop/appInfoModify";
	}
	
	@RequestMapping(value="/appInfomodifySave",method=RequestMethod.POST)
	public String appInfoModifySave(AppInfo appInfo,
				HttpSession session,
				HttpServletRequest request,
				@RequestParam(value="attach",required=false)MultipartFile attach) {
logger.info("==============================进入appInfoModifySave方法===============================");
		
		String logoPicPath=null;
		String logoLocPath=null;
		if(!attach.isEmpty()) {
			System.out.println("进入文件处理功能");
			int fileSize=500000;
			List<String> format = Arrays.asList("jpg","png","gif","bmp");
			logoPicPath=attach.getOriginalFilename();
			String extensive=FilenameUtils.getExtension(logoPicPath);
			if(attach.getSize()>fileSize) {
				request.setAttribute("uploadError","文件超出500KB,请上传小于500KB的文件");
				return "develop/appInfoModify";
			}else if(format.contains(extensive)) {
				String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadFile");
				String fileName=System.currentTimeMillis()+RandomUtils.nextInt(0,100000)+"_logoPicPath."+extensive;
				File saveFile=new File(path,fileName);
				if(!saveFile.exists()) {
					saveFile.mkdirs();
				}
				try {
					attach.transferTo(saveFile);
					logoLocPath="statics"+File.separator+"uploadFile"+File.separator+fileName;
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadError", "上传失败");
				    return "develop/appInfoModify";
				}
				
			}else {
				request.setAttribute("uploadError","文件的 格式不正确，请上传，jpg,png,gif,bmp格式的图片");
				return "develop/appInfoModify";
			}
		}
		int sessionId=((DevUser)session.getAttribute(Constants.USER_SESSION)).getId();
		appInfo.setModifyBy(sessionId);
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		boolean result;
		try {
			result = appInfoService.ModifyAppInfo(appInfo);
		if(result) {
			return "redirect:/dev/sys/applist";
		}else {
			request.setAttribute("uploadError","更新失败");
		    return "develop/appInfoModify";
		}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("uploadError", "服务器出错");
		    return "develop/appInfoModify";
		}
	}

	@RequestMapping("/deleteAppInfo/{id}")
	public String deleteAppInfo(@PathVariable String id) {
		boolean result=false;
		try {
			result = appInfoService.deleteAppInfo(id);
			if(result) {
				return "redirect:/dev/sys/applist";
			}else {
				return "develop/appinfolist";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "develop/appinfolist";
		}
	}
	
	@RequestMapping("/deletePic")
	@ResponseBody
	public String deletePic(@RequestParam("picPath")String picPath ) {
		String picpath="D:/Develop/apache-tomcat-8.0.53/webapps/AppInfoSys/"+picPath;
		File file=new File(picpath);
		logger.info("=============================进入删除图片信息的方法====================");
		logger.info("图片位置："+picpath);
		if(file.exists()) {
			file.delete();
		}
		return "{\"result\":\"true\"}";
	}
}

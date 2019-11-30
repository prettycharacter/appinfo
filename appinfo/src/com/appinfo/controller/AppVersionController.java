package com.appinfo.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.appinfo.pojo.AppVersion;
import com.appinfo.pojo.DevUser;
import com.appinfo.service.appinfo.AppInfoService;
import com.appinfo.service.appversion.AppVersionService;
import com.appinfo.utils.constant.Constants;

/**
 * APP版本控制器
 * @author Rare
 *
 */
@Controller
@RequestMapping("/dev/appversion")
public class AppVersionController {
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private AppVersionService appVersionService;
	
	
	
	@RequestMapping(value = "/appversionmodifysave",method=RequestMethod.POST)
	public String modifyAppVersionSave(@ModelAttribute AppVersion appVersion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required= false) MultipartFile attach){	
			String downloadLink =  null;
			String apkLocPath = null;
			String apkFileName = null;
			if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
				if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
					 String apkName = null;
					 try {
						apkName = appInfoService.getAppInfoById(appVersion.getAppId()).getAPKName();
					 } catch (Exception e1) {
						e1.printStackTrace();
					 }
					 if(apkName == null || "".equals(apkName)){
						 return "redirect:/dev/app/appversionmodify?vid="+appVersion.getId()
								 +"&aid="+appVersion.getAppId();
				
					 }
					 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
					 File targetFile = new File(path,apkFileName);
					 if(!targetFile.exists()){
						 targetFile.mkdirs();
					 }
					 try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						 return "redirect:/dev/app/appversionmodify?vid="+appVersion.getId()
						 +"&aid="+appVersion.getAppId();
					} 
					downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
					apkLocPath = path+File.separator+apkFileName;
				}else{
					 return "redirect:/dev/app/appversionmodify?vid="+appVersion.getId()
					 +"&aid="+appVersion.getAppId();
				}
			}
			appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appVersion.setModifyDate(new Date());
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setApkFileName(apkFileName);
			try {
				if(appVersionService.modify(appVersion)){
					return "redirect:/dev/app/list";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "developer/appversionmodify";
			}
				
				/**
				 * 跳转到版本修改页面
				 * @param versionId
				 * @param appId
				 * @param model
				 * @return
				 */
				@RequestMapping(value="/appversionmodify",method=RequestMethod.GET)
				public String modifyAppVersion(@RequestParam("vid") String versionId,
												@RequestParam("aid") String appId,
												Model model) {
					AppVersion appVersion = null;
					try {
						appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
						List<AppVersion> appVersionList = appVersionService.listAppversionByAppId(Integer.parseInt(appId));
						model.addAttribute(appVersion);
						model.addAttribute("appVersionList",appVersionList);
					}catch(Exception e) {
						e.printStackTrace();
					}
					return "developer/appversionmodify";
				}

	
	/**
	 * 保存新增appversion信息
	 * @param appVersion
	 * @param session
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addVersionSave(@ModelAttribute AppVersion appVersion,HttpSession session,HttpServletRequest request,
						@RequestParam(value="a_downloadLink",required= false) MultipartFile attach ){		
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;
				 try {
					apkName = appInfoService.getAppInfoById(appVersion.getAppId()).getAPKName();
				 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/dev/flatform/app/appversionadd?id="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/app/appversionadd/"+appVersion.getAppId();
							
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/dev/app/appversionadd/"+appVersion.getAppId();
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		try {
			if(appVersionService.appVersionAddSave(appVersion)){
				return "redirect:/dev/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/dev/app/appversionadd/"+appVersion.getAppId();
	}
	
	/**
	 * 跳转到appversion添加页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appversionadd/{id}" ,method=RequestMethod.GET)
	public String toAddAppVersion(@PathVariable Integer id , Model model,
			AppVersion appVersion) {
		try {
			appVersion.setAppId(id);
			List<AppVersion> appVersionList = appVersionService.listAppversionByAppId(id);
			appVersion.setAppName((appInfoService.getAppInfoById(id)).getSoftwareName());
			model.addAttribute("appVersionList", appVersionList);
			model.addAttribute(appVersion);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "developer/appversionadd";
	}
}

package com.appinfo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appinfo.pojo.BackendUser;
import com.appinfo.pojo.DevUser;
import com.appinfo.service.backenduser.BackendUserService;

/**
 * 后台管理控制器
 * @author Rare
 *
 */
@Controller
@RequestMapping("manager")
public class BackendUserController {
	@Resource
	private BackendUserService backendUserService;
	
	/**
	 * 管理者退出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String doLogout(HttpSession session) {
		session.removeAttribute("userSession");
		return "redirect:/manager/login";
	}
	
	/**
	 * 跳转到管理者首页
	 * @return
	 */
	@RequestMapping(value="/main")
	public String toDevMain() {
		return "backend/main";
	}
	
	/**
	 * 管理者登录
	 * @param userCode
	 * @param userPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doDevLogin(@RequestParam String userCode,
							 @RequestParam String userPassword,HttpServletRequest request) {
		BackendUser loginUser = backendUserService.login(userCode,userPassword);
		if(null == loginUser) {
			request.setAttribute("error", "用户名或密码错误!");
			return "backendlogin";
		}
		request.getSession().setAttribute("userSession", loginUser);
		return "redirect:/manager/main";
	}
	
	/**
	 * 跳转到后台管理登录界面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toLogin() {
		return "backendlogin";
	}
	
}

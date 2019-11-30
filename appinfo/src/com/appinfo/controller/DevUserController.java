package com.appinfo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appinfo.pojo.DevUser;
import com.appinfo.service.devuser.DevUserService;


/**
 * 开发者控制器
 * @author Rare
 *
 */
@Controller
@RequestMapping("/dev")
public class DevUserController {

	@Resource
	private DevUserService devUserService;
	
	/**
	 * 开发者退出
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String doDevLogout(HttpSession session) {
		session.removeAttribute("devLoginUser");
		return "redirect:/dev/login";
	}
	
	/**
	 * 跳转到开发者首页
	 * @return
	 */
	@RequestMapping(value="/main")
	public String toDevMain() {
		return "developer/main";
	}
	
	/**
	 * 开发者登录
	 * @param devCode
	 * @param devPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doDevLogin(@RequestParam String devCode,
							 @RequestParam String devPassword,HttpServletRequest request) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(null == loginUser) {
			request.setAttribute("error", "用户名或密码错误!");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	/**
	 * 跳转到开发者登录界面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toDevLogin() {
		return "devlogin";
	}
}

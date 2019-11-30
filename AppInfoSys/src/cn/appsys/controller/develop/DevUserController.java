package cn.appsys.controller.develop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.StringUtils;

import cn.appsys.controller.BaseController;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.develop.devuser.DevUserService;
import cn.appsys.utils.Constants;
@Controller
@RequestMapping(value="/dev")
public class DevUserController extends BaseController {
	private Logger logger=Logger.getLogger(DevUserController.class);
	@Resource
	private DevUserService devUserService;
	//跳转到登录页面
	@RequestMapping(value="/login",method=RequestMethod.GET) 
	public String login() {
		logger.info("DevUserLogin====================================");
		return "devlogin";
	}
	//做登录处理
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String devUserCode,
			              @RequestParam String devPassword,
			              HttpServletRequest request,
			              HttpSession session){
			try {
				DevUser devUser=devUserService.devLogin(devUserCode, devPassword);
			    if(devUser!=null) {
			    	session.setAttribute(Constants.USER_SESSION,devUser);
			    	return "redirect:/dev/sys/main";
			    }
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error","服务器异常");
				return "devlogin";
			}
		   request.setAttribute("error","登录用户名或者密码错误");
		   return "devlogin";
		}
	
	@RequestMapping(value="/sys/main",method=RequestMethod.GET)
	public String main(HttpSession session) {
		if(session.getAttribute(Constants.USER_SESSION)==null) {
			return "devlogin";
		}
		return "develop/main";
	}
	@RequestMapping(value="/loginout")
	public String loginOut(HttpSession session) {
		  session.removeAttribute(Constants.USER_SESSION);
		  return "redirect:/dev/login";
	}
}

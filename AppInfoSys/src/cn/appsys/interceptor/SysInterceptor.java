package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.utils.Constants;

public class SysInterceptor extends HandlerInterceptorAdapter {
      private Logger logger=Logger.getLogger(SysInterceptor.class);
      public boolean preHandle(HttpServletRequest request,
    		                    HttpServletResponse response,
    		                    Object handler)throws Exception{
    	  logger.debug("进入Sys拦截器，检测用户是否登录");
    	  HttpSession session=request.getSession();
    	  Object obj=session.getAttribute(Constants.USER_SESSION);
    	  if(obj==null) {//&&obj instanceof DevUser
    		  response.sendRedirect(request.getContextPath()+"/401.jsp");
    		  return false;
    	  }
    	  return true;
      }
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<c:set value="${pageContext.request.contextPath}" var="basePath"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>开发者登录平台 </title>

  <!-- Bootstrap -->
    <link href="${basePath}/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${basePath}/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${basePath}/statics/css/nprogress.css" rel="stylesheet">
      <!-- Animate.css -->
    <link href="${basePath}/statics/css/animate.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="${basePath}/statics/css/custom.min.css" rel="stylesheet">
  </head>

   <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="${basePath}/dev/dologin" method="post">
              <h1>开发者登录平台</h1>
              <div>
                <input type="text" class="form-control" name="devUserCode" placeholder="用户名" required="" />
              </div>
              <div>
                <input type="password" class="form-control" name="devPassword" placeholder="密码" required="" />
              </div>
             <span>${error}</span> 
              <div>
                <button  class="btn btn-default" type="submit"  >登录</button>
                <button  class="btn btn-default" type="reset"  >重置</button>
                </div>
              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">第一次来?
                  <a href="#signup" class="to_register"> 创建账户 </a>
                </p>

                <div class="clearfix"></div>
                <br />
                <div>
                  <h1></h1>
                  <p>©2018 @Author 许鹏飞    283070153@qq.com</p>
                </div>
              </div>
            </form>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>创建账户</h1>
              <div>
                <input type="text" class="form-control" placeholder="用户名" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="邮箱" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="密码" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">注册</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">已经有账号？
                  <a href="#signin" class="to_register"> 登录 </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <p>©2018 @Author 许鹏飞    283070153@qq.com</p>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
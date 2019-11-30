<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set value="${pageContext.request.contextPath}" var="basePath"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>APP信息管理系统</title>

<!-- Bootstrap -->
<link href="${basePath}/statics/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="${basePath}/statics/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="${basePath}/statics/css/nprogress.css" rel="stylesheet">
<!-- Animate.css -->
<link href="${basePath}/statics/css/animate.min.css" rel="stylesheet">
<!-- Custom Theme Style -->
<link href="${basePath}/statics/css/custom.min.css" rel="stylesheet">
<link href="${basePath}/statics/localcss/author.css" rel="stylesheet">
</head>

<body class="login">
	<div>
		<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
			id="signin"></a>

		<div class="login_wrapper">
			<div class="animate form login_form">
				<section class="login_content">
				<form>
					<h1>APP信息管理系统</h1>
					<div>
						<a class="btn btn-link" href="index.html">后台管理系统入口</a>
					</div>
					<div>
						<a class="btn btn-link" href="dev/login">开发者平台入口</a>
					</div>
						<div>
						<h1></h1>
	                     <p>©2018 @Author 许鹏飞 283070153@qq.com</p>
                        </div>
				</form>
				</section>
			</div>
		</div>
	</div>
</body>
</html>
 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加app信息</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<div class="x_content">

		<form action="${basePath}/dev/sys/appInfomodifySave" method="post" class="form-horizontal form-label-left" 
		  enctype="multipart/form-data">
			<span class="section">修改App基础信息<i class="fa fa-user"></i>${userSession.devCode}</span>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="softwareName">软件名称
					<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				
				    <input id="appInfoid" name="id" value="${appInfo.id}" type="hidden"/>
				    <input id="basePath" value="${basePath}" type="hidden"/>
					<input id="softwareName" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" name="softwareName"
						placeholder="请输入软件名称" required="required"
						type="text" value="${appInfo.softwareName}"><font color="red"></font>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="APKName">APK名称
					<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input  type="text" name="APKName" id="APKName" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" 
						placeholder="请输入APK名称" required="required" value="${appInfo.APKName}"><font color="red"></font>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="supportROM">支持ROM 
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input  type="text" name="supportROM" id="supportROM" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" 
						placeholder="请输入支持的 ROM" required="required" value="${appInfo.supportROM}">
						<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="interfaceLanguage">界面语言 
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input  type="text" name="interfaceLanguage" id="interfaceLanguage" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" 
						placeholder="请输入软件支持的界面语言" required="required" value="${appInfo.interfaceLanguage}"/>
						<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="interfaceLanguage">软件大小
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input  type="number" name="softwareSize" id="softwareSize" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" 
						placeholder="请输入软件大小单位为 Mb" required="required" value="${appInfo.softwareSize}"/>
						<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="downloads">下载次数
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input  type="number" name="downloads" id="downloads" class="form-control col-md-7 col-xs-12"
						data-validate-length-range="6" data-validate-words="2" 
						placeholder="请输入下载次数单位为：次" required="required" value="${appInfo.downloads}"/>
						<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="flatformId">所属平台
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<select class="form-control" id="flatformId" name="flatformId" >
				    <option value="0" selected="selected">请选择</option>
				    <c:if test="${dataFlatformList!=null}">
									   <c:forEach items="${dataFlatformList}" var="flatform">
									     <option value="${flatform.valueId}" <c:if test="${flatform.valueId==appInfo.flatformId}">selected="selected"</c:if>>${flatform.valueName}</option>
									   </c:forEach>
								     </c:if>
				</select>
				<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="categoryLevel1">一级分类
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<select class="form-control" name="categoryLevel1" id="querycategoryLevel1" >
				<option value="0" selected="selected">请选择</option>
				    <c:forEach items="${appCategory1List}" var="category1">
				    	<option value="${category1.id}" <c:if test="${category1.id==appInfo.categoryLevel1}">selected="selected"</c:if>>${category1.categoryName}</option>
				    </c:forEach>
				</select>
				<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="categoryLevel2">二级分类
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<input type="hidden" name="resultqueryLevel2" id="resultqueryLevel2" value="${appInfo.categoryLevel2 }"/>
				<select class="form-control" name="categoryLevel2" id="querycategoryLevel2">
				    
				</select>
				<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="categoryLevel3">三级分类
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				 <input type="hidden" name="resultqueryLevel3" id="resultqueryLevel3" value="${appInfo.categoryLevel3}"/>
				<select class="form-control" name="categoryLevel3" id="querycategoryLevel3" >
				    
				</select>
				<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">APP状态
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
				<select class="form-control" name="status" id="status" disabled="disabled">
				 <c:forEach items="${dataStatusList}" var="status1">
				    	<option value="${status1.id}" <c:if test="${status1.valueId==appInfo.status}">selected="selected"</c:if>>${status1.valueName}</option>
				    </c:forEach>
				 </select>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12"
					for="appInfo">应用简介 <span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<textarea  required="required" name="appInfo" id="appInfo"
						class="form-control col-md-7 col-xs-12">${appInfo.appInfo}</textarea>
						<span  style="color:red"></span>
				</div>
			</div>
			<div class="item form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">LOGO删除
				<span class="required">*</span>
				</label>
				<div class="col-md-6 col-sm-6 col-xs-12" id="PicDiv">
				<c:if test="${appInfo.logoLocPath!=null}">
					<img src="${pageContext.request.contextPath}/${appInfo.logoLocPath}" width="50px" height="30px" alt="软件图标"></img>
					<input type="hidden" id="picPathxx" value="${appInfo.logoLocPath}">
					<a href="javascript:deletePic()">删除</a>&nbsp;&nbsp;&nbsp;
					<font color="red">请在重新上传之前，删除原来的图像</font>
                </c:if>
                </div>
                </div>
                <div class="item form-group">
                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">LOGO上传
				<span class="required">*</span>
				</label>
                <div class="col-md-6 col-sm-6 col-xs-12">
                
                    <input type="file" class="form-control col-md-7 col-xs-12" name="attach" id="attach"> 
					
					<span color="red">${uploadError}</span>
				
			   </div>
		    </div>
			
			<div class="form-group">
				<div class="col-md-6 col-md-offset-3">
					<button id="send" type="submit" class="btn btn-success">提交</button>
					<button type="reset" class="btn btn-primary">重置</button>
					<a href="javascript:history.go(-1);" class="btn btn-primary">返回上一页</a>
				</div>
			</div>
		</form>
		 <div class="form-group">
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    </div>
	</div>
	<script type="text/javascript" src="${basePath}/statics/localjs/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${basePath}/statics/localjs/modifyappinfo.js"></script>

	<%@include file="footer.jsp"%>
</body>
</html>
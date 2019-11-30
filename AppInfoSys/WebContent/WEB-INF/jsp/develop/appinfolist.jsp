<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						APP信息管理维护<small><i class="fa fa-user"></i>${userSession.devCode}:你可以通过搜索或者其他的筛选对APP的信息进行修改、删除等管理操作*^-^</small>
					</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form id="demo-form2" action="${basePath}/dev/sys/applist" method="post"
						class="form-horizontal form-label-left">

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">软件名称: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
								<input type="text" id="querysoftwareName"
									name="querysoftwareName" value="${querysoftwareName}"
									class="form-control col-md-7 col-xs-12">
							</div>
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">APP状态: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
								<select id="querystatus" name="querystatus" class="form-control">
								    <option value="">--请选择--</option>
								     <c:if test="${dataStatusList!=null}">
									   <c:forEach items="${dataStatusList}" var="status">
									  
									    <option value="${status.valueId}"  <c:if test="${status.valueId==querystatus}">selected="selected"</c:if>>${status.valueName}</option>
									   </c:forEach>
								     </c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">所属平台: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
								<select id="queryflatformId" name="queryflatformId" class="form-control">
									<option value="">--请选择--</option>
								     <c:if test="${dataFlatformList!=null}">
									   <c:forEach items="${dataFlatformList}" var="flatform">
									     <option value="${flatform.valueId}" <c:if test="${flatform.valueId==queryflatformId}">selected="selected"</c:if>>${flatform.valueName}</option>
									   </c:forEach>
								     </c:if>
								</select>
							</div>
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">一级分类: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
								<select id="querycategoryLevel1" name="querycategoryLevel1" class="form-control">
									<option value="">--请选择--</option>
								     <c:if test="${appCategory1List!=null}">
									   <c:forEach items="${appCategory1List}" var="category1">
									     <option value="${category1.id}" <c:if test="${category1.id==querycategoryLevel1}">selected="selected"</c:if>>${category1.categoryName}</option>
									   </c:forEach>
								     </c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">二级分类: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
							  <input type="hidden" name="resultqueryLevel2" id="resultqueryLevel2" value="${querycategoryLevel2}"/>
								<select type="hidden" id="querycategoryLevel2" name="querycategoryLevel2" class="form-control">
						         <c:if test="${querycategoryLevel2!=null}">
						          <option value=>
						         </c:if>
								</select>
							</div>
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="first-name">三级分类: </label>
							<div class="col-md-2 col-sm-12 col-xs-12 form-group">
							 <input type="hidden" name="resultqueryLevel3" id="resultqueryLevel3" value="${querycategoryLevel3}"/>
								<select id="querycategoryLevel3" name="querycategoryLevel3" class="form-control">

								</select>
							</div>
						</div>
						<input type="hidden" name="querypageIndex" id="querypageIndex" value="1"/>
						<input type="hidden" value="${basePath}" id="basePath" name="basePath"/>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
								<button type="submit" class="btn btn-primary">查&nbsp;&nbsp;询</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="appInfoList">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_content">
					<br />
					<button type="button" onclick="addAppInfo();" class="btn btn-success">新增APP基础信息</button>
					<table id="datatable" class="table table-striped table-bordered">
                      <thead>
                       <tr>
                          <td>软件名称</td>
                          <td>APK名称</td>
                          <td>软件大小单位M</td>
                          <td>所属平台</td>
                          <td>所属分类 (一级、二级、三级)</td>
                          <td>状态</td>
                          <td>下载次数</td>
                          <td>最新版本号</td>
                          <td>操作</td>
                        </tr>
                      </thead>


                      <tbody>
                      <c:if test="${appInfoList!=null}">
                       <c:forEach items="${appInfoList}" var="appInfo">
                        <tr>
                          <td>${appInfo.softwareName }</td>
                          <td>${appInfo.APKName }</td>
                          <td>${appInfo.softwareSize }</td>
                          <td>${appInfo.flatformName }</td>
                          <td>${appInfo.categoryLevel1Name}—>${appInfo.categoryLevel2Name}—>${appInfo.categoryLevel3Name}</td>
                          <td>${appInfo.statusName }</td>
                          <td>${appInfo.downloads }</td>
                          <td>${appInfo.versionNo }</td>
                          <td><div class="btn-group">
                    <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle btn-sm" type="button" aria-expanded="false">点击操作<span class="caret"></span>
                    </button>
                    <ul role="menu" class="dropdown-menu">
                      <li><a href="#">下架</a>
                      </li>
                      <li><a href="${basePath}/dev/sys/addVersion/${appInfo.id}">新增版本</a>
                      </li>
                      <li><a href="${basePath}/dev/sys/modifyVersion/${appInfo.id}">修改版本</a>
                      </li>
                      <li><a href="${basePath}/dev/sys/appInfoModify/${appInfo.id}">修改</a>
                      </li>
                      <li><a href="${basePath}/dev/sys/deleteAppInfo/${appInfo.id}">删除</a>
                      </li>
                      <li><a href="#">查看</a>
                      </li>
                    </ul>
                    </div></td>
                        </tr>
                        </c:forEach>
                        </c:if>
                      </tbody>
                    </table>
                    <div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
							<c:if test="${pageBean.currentPageNo==1}">
								<button type="button"  class="btn btn-default">首&nbsp;&nbsp;页</button>&nbsp;&nbsp;
								<button type="button"  class="btn btn-default">上一页</button>&nbsp;&nbsp;
							</c:if>
							<c:if test="${pageBean.currentPageNo>1}">
								<button type="button" onclick="jump_to(1);" class="btn btn-primary">首&nbsp;&nbsp;页</button>&nbsp;&nbsp;
								<button type="button" onclick="jump_to(${pageBean.currentPageNo-1});" class="btn btn-primary">上一页</button>&nbsp;&nbsp;
							</c:if>
							<c:if test="${pageBean.currentPageNo==pageBean.totalPage}">
								<button type="button" class="btn btn-default">下一页</button>&nbsp;&nbsp;
								<button type="button" class="btn btn-default">末&nbsp;&nbsp;页</button>
						    </c:if>
							<c:if test="${pageBean.currentPageNo<pageBean.totalPage}">
								<button type="button" onclick="jump_to(${pageBean.currentPageNo+1});" class="btn btn-primary">下一页</button>&nbsp;&nbsp;
								<button type="button" onclick="jump_to(${pageBean.totalPage});" class="btn btn-primary">末&nbsp;&nbsp;页</button>
						    </c:if>
								<button type="button" class="btn btn-default">当前页[${pageBean.currentPageNo}/${pageBean.totalPage}]</button>
							</div>
				   </div>
				    <div class="form-group">
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    <br/>
				    </div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript" src="${basePath}/statics/localjs/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${basePath}/statics/localjs/appinfolist.js"></script>
		<%@include file="footer.jsp"%>
</body>
</html>
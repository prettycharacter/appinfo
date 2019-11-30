var basePath=$("#basePath").val();
//有用ajax实现的分页的想法，但是量太大
function appInfoList(){ 
	var querysoftwareName = $("#querysoftwareName").val();
	var querystatus = $("#querystatus").val();
	var queryflatformId=$("#queryflatformId").val();
	var querycategoryLevel1=$("#querycategoryLevel1").val();
	var querycategoryLevel2=$("#querycategoryLevel2").val();
	var querycategoryLevel3=$("#querycategoryLevel3").val();
	var querypageIndex=$("#querypageIndex").val();
	$.ajax({
		url:basePath+"/dev/sys/applist2",
		type:"POST",
		data:{"querysoftwareName":querysoftwareName,"querystatus":querystatus,"queryflatformId":queryflatformId,
			  "querycategoryLevel1":querycategoryLevel1,"querycategoryLevel2":querycategoryLevel2,
			  "querycategoryLevel3":querycategoryLevel3,"querypageIndex":querypageIndex},
		dataType:"html",
		success:function(data){
			
		},
		error:function(){
			alert("服务器异常请重试！")
		}
	});
}
//跳页方法
function jump_to(num){
	$("#querypageIndex").val(num);
	$("#demo-form2").submit();
}
//一级标题改变事件，刷新二级标题
$("#querycategoryLevel1").change(function (){
	categoryL2();
});
function categoryL2(){
	var pid=$("#querycategoryLevel1").val();
	if(pid==0){
		$("#querycategoryLevel2").html("");
		$("#querycategoryLevel3").html("");
		return;
	}
	var $category2=$("#querycategoryLevel2").html("");
	$.ajax({
		url:basePath+"/dev/sys/categoryLevel",
		type:"GET",
		data:{"parentId":pid},
		dataType:"JSON",
		success:function(result){
		    if(result==null||result.length==0){
		    	$category2.html("暂无数据！");
		    }else{
		    	var resultqueryLevel2=$("#resultqueryLevel2").val();
		    	var option="<option value=\"0\" >请选择</option>";
		    	for(var i=0; i<result.length; i++){
		    		if(result[i].id==resultqueryLevel2){
		    		option+="<option value='"+result[i].id+"' selected='selected'>"+result[i].categoryName+"</option>";
		    	   }else{
		    		option+="<option value='"+result[i].id+"'>"+result[i].categoryName+"</option>";
		    	   }
		       }
		    	$category2.append(option);
		    }
		},
		error:function(){
			alter("服务器出现错误！请重试");
		}
	});
}
$("#querycategoryLevel2").change(function (){
	categoryL3();
});
function categoryL3(){
	var pid=$("#querycategoryLevel2").val();
	if(pid==0){
		var $category3=$("#querycategoryLevel3").html("");
		return;
	}
	if(pid==null){
		pid=$("#resultqueryLevel2").val();
	}
	var $category3=$("#querycategoryLevel3").html("");
	$.ajax({
		url:basePath+"/dev/sys/categoryLevel",
		type:"GET",
		data:{"parentId":pid},
		dataType:"JSON",
		success:function(result){
			if(result==null||result.length==0){
				$category3.html("暂无数据！");
			}else{
				var resultqueryLevel3=$("#resultqueryLevel3").val();
				var option="<option value=\"0\" >请选择</option>";
				for(var i=0; i<result.length; i++){
					if(result[i].id==resultqueryLevel3){
						option+="<option value='"+result[i].id+"' selected='selected'>"+result[i].categoryName+"</option>";
					}else{
						option+="<option value='"+result[i].id+"'>"+result[i].categoryName+"</option>";
					}
				}
				$category3.append(option);
			}
		},
		error:function(){
			alter("服务器出现错误！请重试");
		}
});
}

$(function(){
	if($("#resultqueryLevel2").val()>0){
		categoryL2();
	}
	if($("#resultqueryLevel3").val()>0){
		categoryL3();
	}
})
function addAppInfo(){
	location.href=basePath+"/dev/sys/addappInfo";
}

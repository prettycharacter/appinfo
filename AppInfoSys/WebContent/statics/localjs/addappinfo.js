var $softwareName=$("#softwareName");
	var basePath=$("#basePath").val();
	var $ApkName=$("#APKName");
	$softwareName.blur(function (){
		var softwareName=$softwareName.val();
		$.ajax({
			url:basePath+"/dev/sys/softwareNameisExist",
			type:"GET",
			data:{"softwareName":softwareName},
			dataType:"JSON",
			success:function(data){
				if(data.flag=="true"){
					$softwareName.next().html("用户名可以使用");
				}else if(data.flag=="false"){
					$softwareName.next().html("用户名已经存在");
				}else if(data.flag=="error"){
					$softwareName.next().html("用户名不能为空");
				}
			},
			error:function(){
				alert("服务器出现错误！");
			}
		});
	});
	$ApkName.blur(function(){
		var APKName=$ApkName.val();
		$.ajax({
			url:basePath+"/dev/sys/APKNameisExist",
			type:"GET",
			data:{"APKName":APKName},
			dataType:"JSON",
			success:function(data){
				if(data.flag == "true"){
					$ApkName.next().html("APK名称可以使用");
				}else if(data.flag == "false"){
					$ApkName.next().html("APK名称已经存在");
				}else if(data.flag == "error"){
					$ApkName.next().html("APK名称不能为空");
				}
			},
			error:function(){
				alert("服务器出现错误！");
			}
		});
	});
//一级标题改变事件，刷新二级标题
$("#querycategoryLevel1").change(function (){
	categoryL2();
});
function categoryL2(){
	var pid=$("#querycategoryLevel1").val();
	alert(pid);
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
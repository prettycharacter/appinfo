package cn.appsys.dao.datadictionary;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
    //获取状态信息列表
	List<DataDictionary> getDataDictionaryList(@RequestParam("typeCode")String typeCode);
	
}

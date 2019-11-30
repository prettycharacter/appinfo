package cn.appsys.service.develop.datadictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
    //通过typeCode来获取Status列表
	List<DataDictionary> getDataDictionaryList(String typeCode)throws Exception;
}

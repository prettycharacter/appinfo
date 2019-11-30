package com.appinfo.service.datadictionary;

import java.util.List;

import com.appinfo.pojo.DataDictionary;

public interface DataDictionaryService {

	/**
	 * 查询App状态、App平台列表
	 * @param typeCode
	 * @return
	 */
	List<DataDictionary> listDataDictionaryByTypeCode(String typeCode);

}

package com.appinfo.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.appinfo.pojo.DataDictionary;

public interface DataDictionaryMapper {

	/**
	 * 根据typeCode查询数据字典信息
	 * @param typeCode
	 * @return
	 */
	List<DataDictionary> listDataDictionaryByTypeCode(@Param("typeCode")String typeCode);
	
}

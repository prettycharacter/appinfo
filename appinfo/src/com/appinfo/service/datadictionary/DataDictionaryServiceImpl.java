package com.appinfo.service.datadictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appinfo.dao.datadictionary.DataDictionaryMapper;
import com.appinfo.pojo.DataDictionary;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Override
	public List<DataDictionary> listDataDictionaryByTypeCode(String typeCode) {
		return dataDictionaryMapper.listDataDictionaryByTypeCode(typeCode);
	}

}

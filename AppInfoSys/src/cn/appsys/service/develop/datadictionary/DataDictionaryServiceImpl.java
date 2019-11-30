package cn.appsys.service.develop.datadictionary;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;


@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	//获得状态信息列表
	@Override
	public List<DataDictionary> getDataDictionaryList(String typeCode) throws Exception {
		List<DataDictionary> dataDictionaryList=null;
		try {
		dataDictionaryList=dataDictionaryMapper.getDataDictionaryList(typeCode);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataDictionaryList;
	}

}

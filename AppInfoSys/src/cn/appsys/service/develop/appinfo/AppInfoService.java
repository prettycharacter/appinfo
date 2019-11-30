package cn.appsys.service.develop.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	/**查看APKName是否重复
	 * @param APKName
	 * @return
	 * @throws Exception
	 */
	public boolean APKNameisExist(String APKName)throws Exception;
	/**查看软件名称是否存在
	 * @param softwareName
	 * @return
	 * @throws Exception
	 */
	public boolean softwareNameisExist(String softwareName)throws Exception;
	/**根据条件获取APP的数量
	 * @param devId
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public int getAppInCount( Integer devId,
			                            String softwareName,
										Integer status,
										Integer flatformId,
										Integer categoryLevel1,
										Integer categoryLevel2,
										Integer categoryLevel3,
										Integer pageIndex,
										Integer pageSize)throws Exception;
	
	/**根据条件获取App信息列表
	 * @param devId  开发者Id
	 * @param softwareName 软件名称
	 * @param status 状态编号
	 * @param flatformId 所属平台编号
	 * @param categoryLevel1 一级标题编号
	 * @param categoryLevel2 二级标题编号
	 * @param categoryLevel3 三级标题编号
	 * @param pageIndex 截取索引值
	 * @param pageSize 页面容量
	 * @return
	 */
	public List<AppInfo> getAppInfoList(Integer devId,
							            String softwareName,
										Integer status,
										Integer flatformId,
										Integer categoryLevel1,
										Integer categoryLevel2,
										Integer categoryLevel3,
										Integer pageIndex,
										Integer pageSize)throws Exception;
	/**增加app信息
	 * @param appInfo
	 * @return
	 */
	public boolean addAppInfo(AppInfo appInfo)throws Exception;
	/**根据id获取APPInfo信息
	 * @param id
	 * @return
	 */
	public AppInfo getAppInfoById(String id)throws Exception;
	
	/**更新APPInfo基础信息
	 * @param appInfo
	 * @return
	 */
	public boolean ModifyAppInfo(AppInfo appInfo)throws Exception;
	/**删除Info信息
	 * @param id
	 * @return
	 */
	public boolean deleteAppInfo(String id)throws Exception;

	

}

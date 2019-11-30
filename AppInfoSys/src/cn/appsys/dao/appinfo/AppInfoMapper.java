package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	/**判断APKName名称是否存在
	 * @param APKName
	 * @return
	 */
	public int APKNameisExist(@Param("APKName")String APKName);
	/**判断软件名称是否存在
	 * @param softwareName
	 * @return
	 */
	public int softwareNameisExist(@Param("softwareName")String softwareName);
	
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
	public int getAppInCount(@Param("devId")Integer devId,
			@Param("softwareName")String softwareName,
			@Param("status")Integer status,
			@Param("flatformId")Integer flatformId,
			@Param("categoryLevel1")Integer categoryLevel1,
			@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,
			@Param("pageIndex")Integer pageIndex,
			@Param("pageSize")Integer pageSize)throws Exception;
	
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
	public List<AppInfo> getAppInfoList(@Param("devId")Integer devId,
										@Param("softwareName")String softwareName,
										@Param("status")Integer status,
										@Param("flatformId")Integer flatformId,
										@Param("categoryLevel1")Integer categoryLevel1,
										@Param("categoryLevel2")Integer categoryLevel2,
										@Param("categoryLevel3")Integer categoryLevel3,
										@Param("pageIndex")Integer pageIndex,
										@Param("pageSize")Integer pageSize)throws Exception;
	/**增加appInfo信息
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo);
	/**更新基础信息
	 * @param appInfo
	 * @return
	 */
	public int modifyAppInfo(AppInfo appInfo);
	/**删除基础信息
	 * @param id
	 * @return
	 */
	public int deleteAppInfo(@Param("id")String id);
	/**根据Id查询基础信息
	 * @param id
	 * @return
	 */
	public AppInfo getAppInfoById(@Param("id")String id);
	//跟新APP的版本信息
	public int updateVersionId(Integer versionId, Integer appId);

	
}

package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusinessRate;

import java.util.List;

/**
 * 返点比例表Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface BusinessRateMapper 
{
    /**
     * 查询返点比例表
     * 
     * @param id 返点比例表ID
     * @return 返点比例表
     */
    public BusinessRate selectBusinessRateById(Long id);

    /**
     * 查询返点比例表列表
     * 
     * @param businessRate 返点比例表
     * @return 返点比例表集合
     */
    public List<BusinessRate> selectBusinessRateList(BusinessRate businessRate);

    /**
     * 新增返点比例表
     * 
     * @param businessRate 返点比例表
     * @return 结果
     */
    public int insertBusinessRate(BusinessRate businessRate);

    /**
     * 修改返点比例表
     * 
     * @param businessRate 返点比例表
     * @return 结果
     */
    public int updateBusinessRate(BusinessRate businessRate);

    /**
     * 删除返点比例表
     * 
     * @param id 返点比例表ID
     * @return 结果
     */
    public int deleteBusinessRateById(Long id);

    /**
     * 批量删除返点比例表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessRateByIds(String[] ids);



    /**
     * 查询集团返点
     * @param membership 集团名称
     * @return 结果
     */
    public String selectMemberRate(String membership);


    //   判断集团返点率是否存在
    public int selectMemberRateIfExist(BusinessRate businessRate);

    //导出列表
    public List<BusinessRate> selectExportBusinessRateList(BusinessRate businessRate);
}

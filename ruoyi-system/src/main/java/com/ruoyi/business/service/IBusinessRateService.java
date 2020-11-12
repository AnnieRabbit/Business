package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusinessRate;

import java.util.List;

/**
 * 返点比例表Service接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface IBusinessRateService 
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
     * 批量删除返点比例表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessRateByIds(String ids);

    /**
     * 删除返点比例表信息
     * 
     * @param id 返点比例表ID
     * @return 结果
     */
    public int deleteBusinessRateById(Long id);


    /**
     * 导出全部excel操作excel
     * @param lists
     * @return
     */
    List<List<String>> changeExcel(List<BusinessRate> lists);

    /**
     * 导出全部数据
     * @param
     * @returnate
     */
    public List<BusinessRate> selectExportBusinessRateList(BusinessRate businessRate);


}

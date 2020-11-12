package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusinessReceivable;

import java.util.List;

/**
 * 应收账单Service接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface IBusinessReceivableService 
{
    /**
     * 查询应收账单
     * 
     * @param id 应收账单ID
     * @return 应收账单
     */
    public BusinessReceivable selectBusinessReceivableById(Long id);

    /**
     * 查询应收账单列表
     * 
     * @param businessReceivable 应收账单
     * @return 应收账单集合
     */
    public List<BusinessReceivable> selectBusinessReceivableList(BusinessReceivable businessReceivable);

    /**
     * 新增应收账单
     * 
     * @param businessReceivable 应收账单
     * @return 结果
     */
    public int insertBusinessReceivable(BusinessReceivable businessReceivable);

    /**
     * 修改应收账单
     * 
     * @param businessReceivable 应收账单
     * @return 结果
     */
    public int updateBusinessReceivable(BusinessReceivable businessReceivable);

    /**
     * 批量删除应收账单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReceivableByIds(String ids);

    /**
     * 删除应收账单信息
     * 
     * @param id 应收账单ID
     * @return 结果
     */
    public int deleteBusinessReceivableById(Long id);

    /**
     * 导出全部excel操作excel
     * @param lists
     * @return
     */
    List<List<String>> changeExcel(List<BusinessReceivable> lists);

    /**
     * 导出全部数据
     * @param
     * @return
     */
    public List<BusinessReceivable> selectExportBusinessReceivableList(BusinessReceivable businessReceivable);

}

package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusinessReturn;

import java.util.List;

/**
 * 投诉退费Service接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface IBusinessReturnService 
{
    /**
     * 查询投诉退费
     * 
     * @param id 投诉退费ID
     * @return 投诉退费
     */
    public BusinessReturn selectBusinessReturnById(Long id);

    /**
     * 查询投诉退费列表
     * 
     * @param businessReturn 投诉退费
     * @return 投诉退费集合
     */
    public List<BusinessReturn> selectBusinessReturnList(BusinessReturn businessReturn);

    /**
     * 新增投诉退费
     * 
     * @param businessReturn 投诉退费
     * @return 结果
     */
    public int insertBusinessReturn(BusinessReturn businessReturn);

    /**
     * 修改投诉退费
     * 
     * @param businessReturn 投诉退费
     * @return 结果
     */
    public int updateBusinessReturn(BusinessReturn businessReturn);

    /**
     * 批量删除投诉退费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReturnByIds(String ids);

    /**
     * 删除投诉退费信息
     * 
     * @param id 投诉退费ID
     * @return 结果
     */
    public int deleteBusinessReturnById(Long id);


    /**
     * 导出全部excel操作excel
     * @param lists
     * @return
     */
    List<List<String>> changeExcel(List<BusinessReturn> lists);

    /**
     * 导出全部数据
     * @param
     * @return
     */
    public List<BusinessReturn> selectExportBusinessReturnList(BusinessReturn businessReturn);
}

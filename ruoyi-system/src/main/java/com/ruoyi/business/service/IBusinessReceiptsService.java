package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusinessReceipts;

import java.util.List;
import java.util.Map;

/**
 * 实收账单Service接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface IBusinessReceiptsService 
{
    /**
     * 查询实收账单
     * 
     * @param id 实收账单ID
     * @return 实收账单
     */
    public BusinessReceipts selectBusinessReceiptsById(Long id);

    /**
     * 查询实收账单列表
     * 
     * @param businessReceipts 实收账单
     * @return 实收账单集合
     */
    public List<BusinessReceipts> selectBusinessReceiptsList(BusinessReceipts businessReceipts);

    /**
     * 新增实收账单
     * 
     * @param businessReceipts 实收账单
     * @return 结果
     */
    public int insertBusinessReceipts(BusinessReceipts businessReceipts);

    /**
     * 修改实收账单
     * 
     * @param businessReceipts 实收账单
     * @return 结果
     */
    public int updateBusinessReceipts(BusinessReceipts businessReceipts);

    /**
     * 批量删除实收账单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReceiptsByIds(String ids);

    /**
     * 删除实收账单信息
     * 
     * @param id 实收账单ID
     * @return 结果
     */
    public int deleteBusinessReceiptsById(Long id);


    /**
     *展示发展人实收
     */
    public Map<String,Object> countTotal(String inputDate, String developeNum);

    /**
     * 导入数据
     *
     *  @param businessReceiptsList 数据列表
     * @return 结果
     */
    public Map<String, Object> importBusinessReceipts(List<BusinessReceipts> businessReceiptsList);


    /**
     * 导出全部excel操作excel
     * @param lists
     * @return
     */
    List<List<String>> changeExcel(List<BusinessReceipts> lists);

    /**
     * 导出全部数据
     * @param
     * @return
     */
    public List<BusinessReceipts> selectExportBusinessReceiptsList(BusinessReceipts businessReceipts);
}

package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusinessReceipts;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 实收账单Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface BusinessReceiptsMapper 
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
     * 删除实收账单
     * 
     * @param id 实收账单ID
     * @return 结果
     */
    public int deleteBusinessReceiptsById(Long id);

    /**
     * 批量删除实收账单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReceiptsByIds(String[] ids);

    /**
     * 查询当月实收账单
     * @param inputDate 录入数据
     * @param developeNum 发展人
     * @return 结果
     */

    public double selectAdvanceTotal(@Param("inputDate") String inputDate, @Param("developeNum")String developeNum);
    public double selectClearTotal(@Param("inputDate") String inputDate, @Param("developeNum")String developeNum);



    //   判断收账记录是否存在
    public int selectBusinessReceiptsIfExists(BusinessReceipts businessReceipts);


    //导出列表
    public List<BusinessReceipts> selectExportBusinessReceiptsList(BusinessReceipts businessReceipts);

    // 此订单总实收记录数
    public int selectCountsBusinessReceipts(@Param("customerNum")String customerNum, @Param("productId")String productId, @Param("orderTime") Date orderTime);

}

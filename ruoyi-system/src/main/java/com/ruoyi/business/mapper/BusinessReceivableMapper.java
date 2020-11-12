package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusinessReceivable;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 应收账单Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface BusinessReceivableMapper 
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
     * 删除应收账单
     * 
     * @param id 应收账单ID
     * @return 结果
     */
    public int deleteBusinessReceivableById(Long id);

    /**
     * 批量删除应收账单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReceivableByIds(String[] ids);


    //导出列表
    public List<BusinessReceivable> selectExportBusinessReceivableList(BusinessReceivable businessReceivable);

    //   判断实收记录是否存在
    public int selectBusinessReceivableIfExists(BusinessReceivable businessReceivable);

    // 此订单总应收记录数
    public int selectCountsBusinessReceivable(@Param("customerNum")String customerNum, @Param("productId")String productId, @Param("orderTime") Date orderTime);
}

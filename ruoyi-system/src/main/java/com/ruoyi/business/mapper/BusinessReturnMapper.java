package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusinessReturn;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 投诉退费Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface BusinessReturnMapper 
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
     * 删除投诉退费
     * 
     * @param id 投诉退费ID
     * @return 结果
     */
    public int deleteBusinessReturnById(Long id);

    /**
     * 批量删除投诉退费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessReturnByIds(String[] ids);

    public double selectReturnTotal(@Param("inputDate")String inputDate, @Param("developeNum")String developeNum);


    //导出list
    public List<BusinessReturn> selectExportBusinessReturnList(BusinessReturn businessReturn);


    //此订单总退款记录数
    public int selectCountsBusinessReturn(@Param("customerNum")String customerNum, @Param("productId")String productId, @Param("orderTime") Date orderTime);

}

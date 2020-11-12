package com.ruoyi.business.mapper;

import com.ruoyi.business.domain.BusinessUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface BusinessUserMapper 
{
    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    public BusinessUser selectBusinessUserById(Long id);

    /**
     * 查询用户列表
     * 
     * @param businessUser 用户
     * @return 用户集合
     */
    public List<BusinessUser> selectBusinessUserList(BusinessUser businessUser);

    /**
     * 新增用户
     * 
     * @param businessUser 用户
     * @return 结果
     */
    public int insertBusinessUser(BusinessUser businessUser);

    /**
     * 修改用户
     * 
     * @param businessUser 用户
     * @return 结果
     */
    public int updateBusinessUser(BusinessUser businessUser);

    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 结果
     */
    public int deleteBusinessUserById(Long id);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessUserByIds(String[] ids);

//   判断客户订单是否存在
    public int selectBusinessUserIfExists(BusinessUser businessUser);

    //   该产品返点率
    public String selectProductRate(@Param("customerNum")String customerNum, @Param("productId")String productId, @Param("orderTime")Date orderTime);


    //查出集团名称
    public String selectMembership(@Param("customerNum")String customerNum, @Param("productId")String productId, @Param("orderTime")Date orderTime);


    //导出list
    public List<BusinessUser> selectExportBusinessUserList(BusinessUser businessUser);
}

package com.ruoyi.business.service;

import com.ruoyi.business.domain.BusinessUser;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 用户Service接口
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
public interface IBusinessUserService 
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
     * 批量删除用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessUserByIds(String ids);

    /**
     * 删除用户信息
     * 
     * @param id 用户ID
     * @return 结果
     */
    public int deleteBusinessUserById(Long id);


    /**
     * 导入客户数据
     *
     *  @param businessUserList 客户数据列表
     * @return 结果
     */
    public Map<String, Object> importBusinessUser(List<BusinessUser> businessUserList) throws ParseException;

    //是否已存在
    public int selectBusinessUserIfExists(BusinessUser businessUser);


    /**
     * 导出客户订单全部excel操作excel
     * @param lists
     * @return
     */
    List<List<String>> changeExcel(List<BusinessUser> lists);

    /**
     * 导出客户订单全部数据
     * @param
     * @return
     */
    public List<BusinessUser> selectExportBusinessUserList(BusinessUser businessUser);

    /**
     * 发展人列表
     */
    public List<BusinessUser> countList(String inputDate,String developeNum);


    /**
     * 导出发展人订单
     * @param lists
     * @return
     */
    List<List<String>> changeExcelDevelope(List<BusinessUser> lists);

}

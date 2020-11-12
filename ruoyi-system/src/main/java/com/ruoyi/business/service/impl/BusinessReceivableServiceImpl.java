package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusinessReceivable;
import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.mapper.BusinessReceivableMapper;
import com.ruoyi.business.mapper.BusinessUserMapper;
import com.ruoyi.business.service.IBusinessReceivableService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 应收账单Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Service
public class BusinessReceivableServiceImpl implements IBusinessReceivableService 
{
    @Autowired
    private BusinessReceivableMapper businessReceivableMapper;

    @Autowired
    private BusinessUserMapper businessUserMapper;

    /**
     * 查询应收账单
     * 
     * @param id 应收账单ID
     * @return 应收账单
     */
    @Override
    public BusinessReceivable selectBusinessReceivableById(Long id)
    {
        return businessReceivableMapper.selectBusinessReceivableById(id);
    }

    /**
     * 查询应收账单列表
     * 
     * @param businessReceivable 应收账单
     * @return 应收账单
     */
    @Override
    public List<BusinessReceivable> selectBusinessReceivableList(BusinessReceivable businessReceivable)
    {
        return businessReceivableMapper.selectBusinessReceivableList(businessReceivable);
    }

    /**
     * 新增应收账单
     * 
     * @param businessReceivable 应收账单
     * @return 结果
     */
    @Override
    public int insertBusinessReceivable(BusinessReceivable businessReceivable)
    {
        BusinessUser businessUser=new BusinessUser();
        businessUser.setCustomerNum(businessReceivable.getCustomerNum());
        businessUser.setOrderTime(businessReceivable.getOrderTime());
        businessUser.setProductId(businessReceivable.getProductId());
        int a= businessUserMapper.selectBusinessUserIfExists(businessUser);
        if(a>0) {
            int exist = businessReceivableMapper.selectBusinessReceivableIfExists(businessReceivable);
            if (exist > 0) {
                return -2;
            } else {
                return businessReceivableMapper.insertBusinessReceivable(businessReceivable);
            }

        }else{
            return -1;
        }
    }

    /**
     * 修改应收账单
     * 
     * @param businessReceivable 应收账单
     * @return 结果
     */
    @Override
    public int updateBusinessReceivable(BusinessReceivable businessReceivable)
    {
        return businessReceivableMapper.updateBusinessReceivable(businessReceivable);
    }

    /**
     * 删除应收账单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReceivableByIds(String ids)
    {
        return businessReceivableMapper.deleteBusinessReceivableByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除应收账单信息
     * 
     * @param id 应收账单ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReceivableById(Long id)
    {
        return businessReceivableMapper.deleteBusinessReceivableById(id);
    }

    @Override
    public List<List<String>> changeExcel(List<BusinessReceivable> lists) {
        List<List<String>> excelData = new ArrayList<List<String>>();

        List<String> head = new ArrayList<>();
        head.add("客户号码");
        head.add("产品ID");
        head.add("产品名称");
        head.add("订购时间");
        head.add("所属集团");
        head.add("发展人");
        head.add("收账时间");
        head.add("实收金额");
        excelData.add(head);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < lists.size(); i++) {
            List<String> data = new ArrayList<>();
            data.add(lists.get(i).getCustomerNum().equals("")?"":lists.get(i).getCustomerNum());
            data.add(lists.get(i).getProductId().equals("")?"":lists.get(i).getProductId());
            if(lists.get(i).getBusinessUser()!=null){
                data.add(lists.get(i).getBusinessUser().getProductName().equals("")?"":lists.get(i).getBusinessUser().getProductName());
            }else{
                data.add("");
            }
            data.add(sdf.format(lists.get(i).getOrderTime()).equals("")?"":sdf.format(lists.get(i).getOrderTime()));

            if(lists.get(i).getBusinessUser()!=null){
                data.add(lists.get(i).getBusinessUser().getMembership().equals("")?"":lists.get(i).getBusinessUser().getMembership());
            }else{
                data.add("");
            }
            if(lists.get(i).getBusinessUser()!=null){
                data.add(lists.get(i).getBusinessUser().getDevelopeNum().equals("")?"":lists.get(i).getBusinessUser().getDevelopeNum());
            }else{
                data.add("");
            }

            data.add(sdf.format(lists.get(i).getBillDate()).equals("")?"":sdf.format(lists.get(i).getBillDate()));

            if(lists.get(i).getBusinessUser()!=null){
                data.add(lists.get(i).getBusinessUser().getProductAssets().toString().equals("")?"":lists.get(i).getBusinessUser().getProductAssets().toString());
            }else{
                data.add("");
            }

            excelData.add(data);
        }
        return excelData;
    }

    @Override
    public List<BusinessReceivable> selectExportBusinessReceivableList(BusinessReceivable businessReceivable) {
        return businessReceivableMapper.selectExportBusinessReceivableList(businessReceivable);
    }
}

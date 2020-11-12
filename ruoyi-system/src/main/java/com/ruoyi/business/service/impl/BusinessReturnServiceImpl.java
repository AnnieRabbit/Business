package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusinessReturn;
import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.mapper.BusinessReturnMapper;
import com.ruoyi.business.mapper.BusinessUserMapper;
import com.ruoyi.business.service.IBusinessReturnService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉退费Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Service
public class BusinessReturnServiceImpl implements IBusinessReturnService 
{
    @Autowired
    private BusinessReturnMapper businessReturnMapper;

    @Autowired
    private BusinessUserMapper businessUserMapper;

    /**
     * 查询投诉退费
     * 
     * @param id 投诉退费ID
     * @return 投诉退费
     */
    @Override
    public BusinessReturn selectBusinessReturnById(Long id)
    {
        return businessReturnMapper.selectBusinessReturnById(id);
    }

    /**
     * 查询投诉退费列表
     * 
     * @param businessReturn 投诉退费
     * @return 投诉退费
     */
    @Override
    public List<BusinessReturn> selectBusinessReturnList(BusinessReturn businessReturn)
    {
        return businessReturnMapper.selectBusinessReturnList(businessReturn);
    }

    /**
     * 新增投诉退费
     * 
     * @param businessReturn 投诉退费
     * @return 结果
     */
    @Override
    public int insertBusinessReturn(BusinessReturn businessReturn)
    {
        BusinessUser businessUser=new BusinessUser();
        businessUser.setCustomerNum(businessReturn.getCustomerNum());
        businessUser.setOrderTime(businessReturn.getOrderTime());
        businessUser.setProductId(businessReturn.getProductId());
        int a= businessUserMapper.selectBusinessUserIfExists(businessUser);
        if(a>0){
            return businessReturnMapper.insertBusinessReturn(businessReturn);
        }
        return -1;

    }

    /**
     * 修改投诉退费
     * 
     * @param businessReturn 投诉退费
     * @return 结果
     */
    @Override
    public int updateBusinessReturn(BusinessReturn businessReturn)
    {
        return businessReturnMapper.updateBusinessReturn(businessReturn);
    }

    /**
     * 删除投诉退费对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReturnByIds(String ids)
    {
        return businessReturnMapper.deleteBusinessReturnByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投诉退费信息
     * 
     * @param id 投诉退费ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReturnById(Long id)
    {
        return businessReturnMapper.deleteBusinessReturnById(id);
    }

    //导出excel
    @Override
    public List<List<String>> changeExcel(List<BusinessReturn> lists) {
        List<List<String>> excelData = new ArrayList<List<String>>();

        List<String> head = new ArrayList<>();
        head.add("客户号码");
        head.add("产品ID");
        head.add("产品名称");
        head.add("订购时间");
        head.add("所属集团");
        head.add("发展人");
        head.add("投诉类型");
        head.add("予以退费年月");
        head.add("退费金额");
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

            data.add(lists.get(i).getReturnType().equals("")?"":lists.get(i).getReturnType());
            data.add(lists.get(i).getReturnDate().equals("")?"":lists.get(i).getReturnDate());
            data.add(lists.get(i).getReturnAssets().toString().equals("")?"":lists.get(i).getReturnAssets().toString());
            excelData.add(data);
        }
        return excelData;
    }


    //导出列表
    @Override
    public List<BusinessReturn> selectExportBusinessReturnList(BusinessReturn businessReturn) {
        return businessReturnMapper.selectExportBusinessReturnList(businessReturn);
    }
}

package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusinessRate;
import com.ruoyi.business.mapper.BusinessRateMapper;
import com.ruoyi.business.service.IBusinessRateService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 返点比例表Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Service
public class BusinessRateServiceImpl implements IBusinessRateService 
{
    @Autowired
    private BusinessRateMapper businessRateMapper;

    /**
     * 查询返点比例表
     * 
     * @param id 返点比例表ID
     * @return 返点比例表
     */
    @Override
    public BusinessRate selectBusinessRateById(Long id)
    {
        return businessRateMapper.selectBusinessRateById(id);
    }

    /**
     * 查询返点比例表列表
     * 
     * @param businessRate 返点比例表
     * @return 返点比例表
     */
    @Override
    public List<BusinessRate> selectBusinessRateList(BusinessRate businessRate)
    {
        return businessRateMapper.selectBusinessRateList(businessRate);
    }

    /**
     * 新增返点比例表
     * 
     * @param businessRate 返点比例表
     * @return 结果
     */
    @Override
    public int insertBusinessRate(BusinessRate businessRate)
    {
        int exist=businessRateMapper.selectMemberRateIfExist(businessRate);
        if(exist>0){
            return -1;
        }else{
            return businessRateMapper.insertBusinessRate(businessRate);
        }
    }

    /**
     * 修改返点比例表
     * 
     * @param businessRate 返点比例表
     * @return 结果
     */
    @Override
    public int updateBusinessRate(BusinessRate businessRate)
    {
            return businessRateMapper.updateBusinessRate(businessRate);
    }

    /**
     * 删除返点比例表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusinessRateByIds(String ids)
    {
        return businessRateMapper.deleteBusinessRateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除返点比例表信息
     * 
     * @param id 返点比例表ID
     * @return 结果
     */
    @Override
    public int deleteBusinessRateById(Long id)
    {
        return businessRateMapper.deleteBusinessRateById(id);
    }


    //导出excel
    @Override
    public List<List<String>> changeExcel(List<BusinessRate> lists) {
        List<List<String>> excelData = new ArrayList<List<String>>();

        List<String> head = new ArrayList<>();
        head.add("集团名称");
        head.add("集团比例");
        excelData.add(head);

        for (int i = 0; i < lists.size(); i++) {
            List<String> data = new ArrayList<>();
            data.add(lists.get(i).getMembership().equals("")?"":lists.get(i).getMembership());
            data.add(lists.get(i).getMemberRate().equals("")?"":lists.get(i).getMemberRate());
            excelData.add(data);
        }
        return excelData;
    }

    //导出list
    @Override
    public List<BusinessRate> selectExportBusinessRateList(BusinessRate businessRate) {
        return businessRateMapper.selectExportBusinessRateList(businessRate);
    }


}

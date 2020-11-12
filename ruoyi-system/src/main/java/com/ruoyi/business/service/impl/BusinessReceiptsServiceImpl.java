package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusinessReceipts;
import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.mapper.BusinessRateMapper;
import com.ruoyi.business.mapper.BusinessReceiptsMapper;
import com.ruoyi.business.mapper.BusinessReturnMapper;
import com.ruoyi.business.mapper.BusinessUserMapper;
import com.ruoyi.business.service.IBusinessReceiptsService;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实收账单Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Service
public class BusinessReceiptsServiceImpl implements IBusinessReceiptsService {
    @Autowired
    private BusinessReceiptsMapper businessReceiptsMapper;

    @Autowired
    private BusinessUserMapper businessUserMapper;

    @Autowired
    private BusinessRateMapper businessRateMapper;

    @Autowired
    private BusinessReturnMapper businessReturnMapper;

    /**
     * 查询实收账单
     *
     * @param id 实收账单ID
     * @return 实收账单
     */
    @Override
    public BusinessReceipts selectBusinessReceiptsById(Long id) {
        return businessReceiptsMapper.selectBusinessReceiptsById(id);
    }

    /**
     * 查询实收账单列表
     *
     * @param businessReceipts 实收账单
     * @return 实收账单
     */
    @Override
    public List<BusinessReceipts> selectBusinessReceiptsList(BusinessReceipts businessReceipts) {
        return businessReceiptsMapper.selectBusinessReceiptsList(businessReceipts);
    }



    /**
     * 新增实收账单
     *
     * @param businessReceipts 实收账单
     * @return 结果
     */
    @Override
    public int insertBusinessReceipts(BusinessReceipts businessReceipts) {
        BusinessUser businessUser=new BusinessUser();
        businessUser.setCustomerNum(businessReceipts.getCustomerNum());
        businessUser.setOrderTime(businessReceipts.getOrderTime());
        businessUser.setProductId(businessReceipts.getProductId());
        int a= businessUserMapper.selectBusinessUserIfExists(businessUser);
         if(a>0) {
             int exist = businessReceiptsMapper.selectBusinessReceiptsIfExists(businessReceipts);
             if (exist > 0) {
                 return -2;
             } else {
                 //插入时,插入正确返点率
                 BusinessReceipts businessReceipts2 = insertCorrectRate(businessReceipts);
                 return businessReceiptsMapper.insertBusinessReceipts(businessReceipts2);
             }
         }else{
             return -1;
         }

    }

    /**
     * 修改实收账单
     *
     * @param businessReceipts 实收账单
     * @return 结果
     */
    @Override
    public int updateBusinessReceipts(BusinessReceipts businessReceipts) {

            //插入时,插入正确返点率
        int exist = businessReceiptsMapper.selectBusinessReceiptsIfExists(businessReceipts);
        if (exist > 0) {
            return -2;
        }
        else {
            BusinessReceipts businessReceipts2 = insertCorrectRate(businessReceipts);
            return businessReceiptsMapper.updateBusinessReceipts(businessReceipts2);
        }
    }

    /**
     * 删除实收账单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReceiptsByIds(String ids) {
        return businessReceiptsMapper.deleteBusinessReceiptsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除实收账单信息
     *
     * @param id 实收账单ID
     * @return 结果
     */
    @Override
    public int deleteBusinessReceiptsById(Long id) {
        return businessReceiptsMapper.deleteBusinessReceiptsById(id);
    }


    /**
     * 计算实收账单信息
     *
     * @param inputDate   查询日期
     * @param developeNum 发展人
     * @return 结果
     */
    @Override
    public Map<String, Object> countTotal(String inputDate, String developeNum) {
        Map<String, Object> map = new HashMap<>();
        double advanceTotal = businessReceiptsMapper.selectAdvanceTotal(inputDate, developeNum);
        double clearTotal = businessReceiptsMapper.selectClearTotal(inputDate, developeNum);
        double returnTotal = businessReturnMapper.selectReturnTotal(inputDate, developeNum);
        double totalPay=advanceTotal + clearTotal - returnTotal;

        map.put("advanceTotal", advanceTotal);
        map.put("clearTotal", clearTotal);
        map.put("returnTotal", returnTotal);
        map.put("totalPay", String.format("%.2f", totalPay));  //保留两位小数
        return map;

    }

    //导入方法
    @Override
    public Map<String, Object> importBusinessReceipts(List<BusinessReceipts> businessReceiptsList) {
        Map<String, Object> map = new HashMap<>();

        int totalNum = businessReceiptsList.size(); //理想导入总数
        int addNum = 0;                       //实际导入总数
        if (StringUtils.isNull(businessReceiptsList) || businessReceiptsList.size() == 0) {
            map.put("msg", "excel表格中没有表格数据,请重新检查上传!");
            return map;
        }
        for (BusinessReceipts businessReceipts : businessReceiptsList) {
            try {
                Map <String,Object> checkMap2=checkObjFieldIsNull(businessReceipts);
                if(checkMap2.get("flag").equals(false)){
                    map.put("msg", "成功导入"+addNum+"条,共"+totalNum+"条."+"第"+(addNum+1)+"条的"+checkMap2.get("msg"));
                    return map;
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //把导入的日期进行格式化,
                    String stringOrderTime =sdf.format(businessReceipts.getOrderTime());    //转字符串
                    Date revertOrderTime=sdf.parse(stringOrderTime);                  //转date
                    businessReceipts.setOrderTime(revertOrderTime);
                    BusinessUser businessUser=new BusinessUser();
                    businessUser.setCustomerNum(businessReceipts.getCustomerNum());
                    businessUser.setOrderTime(businessReceipts.getOrderTime());
                    businessUser.setProductId(businessReceipts.getProductId());
                    int a= businessUserMapper.selectBusinessUserIfExists(businessUser);
                    if(a==0){
                        map.put("msg", "成功导入" + addNum + "条,共" + totalNum + "条."+"第"+(addNum+1)+"条的"+"客户订单不存在,请检查后重新添加!");
                        return map;
                    }
                    int exist = businessReceiptsMapper.selectBusinessReceiptsIfExists(businessReceipts);
                    if (exist == 0) {
                        BusinessReceipts businessReceipts2 = insertCorrectRate(businessReceipts);
                        this.businessReceiptsMapper.insertBusinessReceipts(businessReceipts2);
                        addNum++;
                    } else {
                        map.put("msg", "成功导入" + addNum + "条,共" + totalNum + "条."+"第"+(addNum+1)+"条的" + "客户" + businessReceipts.getCustomerNum() + "在时间" + businessReceipts.getOrderTime() + "购买产品:" + businessReceipts.getProductId() + "账单日:" + businessReceipts.getBillDate() + "的记录已存在,请勿重复添加!");
                        return map;
                    }
                }
            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }

        }
        map.put("msg", "成功导入" + addNum + "条,共" + totalNum + "条.");
        return map;
    }

    @Override
    public List<List<String>> changeExcel(List<BusinessReceipts> lists) {
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
        head.add("真正返点");
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

            data.add(lists.get(i).getRealRate().equals("")?"":lists.get(i).getRealRate());

            excelData.add(data);
        }
        return excelData;
    }

    @Override
    public List<BusinessReceipts> selectExportBusinessReceiptsList(BusinessReceipts businessReceipts) {
        return businessReceiptsMapper.selectExportBusinessReceiptsList(businessReceipts);
    }

    /****
     * @param object 对象
     * @return 如果对象不为空，且没有空值。返回true，对象为空或者有空值，返回false
     * */
    private  Map<String,Object> checkObjFieldIsNull(Object object) throws IllegalAccessException{
//        List<String> excludeNames = new ArrayList<String>();  //把不用验证非空的属性加入到excludeNameslist中
//        excludeNames.add("realRate");
//        excludeNames.add("id");

        Map<String,Object> map=new HashMap<>();
        String revertName="";
        boolean flag = false;
        if(null!=object){
            for(Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true);                       //在用反射时访问私有变量（private修饰变量）
//                if(!excludeNames.contains(field.getName())){        //如果该字段不在排除验证的字段list中
                    if (field.isAnnotationPresent(Excel.class)){    //获取@Excel标注的注释名,也就是属性中文名
                        Excel mark = field.getAnnotation(Excel.class);
                        revertName = mark.name();
                        if(field.get(object) == null){
                            flag = false;
                            map.put("flag",flag);
                            map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                            return map;
                        }
                        if(field.get(object) != null&&field.get(object).toString().trim().equals("")){ //某个属性为空字符串的情况
                            flag = false;
                            map.put("flag",flag);
                            map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                            return map;
                        }
                    }

//                }

            }
        }else{
            flag=false;
            map.put("flag",flag);
            map.put("msg","表中数据为空,请检查后重新上传!");
            return map;
        }
        map.put("flag",true);
        return map;
    }



    //插入和更新,导入时,插入正确返点率
    private BusinessReceipts insertCorrectRate(BusinessReceipts businessReceipts) {
        Date revertOrderTime=new Date();
        Date date=businessReceipts.getOrderTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date2 =sdf.format(date);
        try {
           revertOrderTime=sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String membership = businessUserMapper.selectMembership(businessReceipts.getCustomerNum(), businessReceipts.getProductId(),businessReceipts.getOrderTime());
        String memberRate = businessRateMapper.selectMemberRate(membership);
        String productRate = businessUserMapper.selectProductRate(businessReceipts.getCustomerNum(), businessReceipts.getProductId(),revertOrderTime);
        String realRate = "0";
        if (membership == null || membership.equals("")) {
            realRate = productRate;
        } else {
            if (memberRate != null && !memberRate.equals("")) {
                realRate = memberRate;
            } else {
                realRate = productRate;
            }
        }

        businessReceipts.setRealRate(realRate);
        return businessReceipts;
    }



}
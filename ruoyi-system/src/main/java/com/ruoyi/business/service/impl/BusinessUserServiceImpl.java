package com.ruoyi.business.service.impl;

import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.mapper.BusinessReceiptsMapper;
import com.ruoyi.business.mapper.BusinessReceivableMapper;
import com.ruoyi.business.mapper.BusinessReturnMapper;
import com.ruoyi.business.mapper.BusinessUserMapper;
import com.ruoyi.business.service.IBusinessUserService;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.VertifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Service
public class BusinessUserServiceImpl implements IBusinessUserService 
{
    @Autowired
    private BusinessUserMapper businessUserMapper;
    @Autowired
    private BusinessReceiptsMapper businessReceiptsMapper;
    @Autowired
    private BusinessReceivableMapper businessReceivableMapper;
    @Autowired
    private BusinessReturnMapper businessReturnMapper;
    @Autowired
    private VertifyUtil utils;

    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public BusinessUser selectBusinessUserById(Long id)
    {
        return businessUserMapper.selectBusinessUserById(id);
    }

    /**
     * 查询用户列表
     * 
     * @param businessUser 用户
     * @return 用户
     */
    @Override
    public List<BusinessUser> selectBusinessUserList(BusinessUser businessUser)
    {
        return businessUserMapper.selectBusinessUserList(businessUser);
    }

    /**
     * 新增用户
     * 
     * @param businessUser 用户
     * @return 结果
     */
    @Override
    public int insertBusinessUser(BusinessUser businessUser)
    {
        int existUser = businessUserMapper.selectBusinessUserIfExists(businessUser);
        if(existUser>0){
            return -1;
        }else{
            return businessUserMapper.insertBusinessUser(businessUser);
        }

    }

    /**
     * 修改用户
     * 
     * @param businessUser 用户
     * @return 结果
     */
    @Override
    public int updateBusinessUser(BusinessUser businessUser)
    {
        return businessUserMapper.updateBusinessUser(businessUser);
    }

    /**
     * 删除用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusinessUserByIds(String ids)
    {

        return businessUserMapper.deleteBusinessUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户信息
     * 
     * @param id 用户ID
     * @return 结果
     */
    @Override
    public int deleteBusinessUserById(Long id)
    {
        System.out.print(id);
        BusinessUser businessUser=businessUserMapper.selectBusinessUserById(id);
        System.out.print(businessUser);
        int a=businessReceiptsMapper.selectCountsBusinessReceipts(businessUser.getCustomerNum(),businessUser.getProductId(),businessUser.getOrderTime());
        int b=businessReceivableMapper.selectCountsBusinessReceivable(businessUser.getCustomerNum(),businessUser.getProductId(),businessUser.getOrderTime());
        int c=businessReturnMapper.selectCountsBusinessReturn(businessUser.getCustomerNum(),businessUser.getProductId(),businessUser.getOrderTime());

        if((a+b+c)==0) {    //退款实收,应收中都没有此客户订单数据
            return businessUserMapper.deleteBusinessUserById(id);
        }else{
            return -1;
        }

    }


    /**
     * 导入客户订单list
     *  @param businessUserList 客户数据列表

     * @return 结果
     */
    @Override
    public Map<String, Object> importBusinessUser(List<BusinessUser> businessUserList) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        List<String> msg = new ArrayList<>();
        int totalNum=businessUserList.size(); //理想导入总数

        int addNum = 0;                       //实际导入总数
        if (StringUtils.isNull(businessUserList) || businessUserList.size() == 0)
        {
            map.put("msg", "excel表格中没有表格数据,请重新检查上传!");
            return map;
        }

        for (BusinessUser businessUser : businessUserList)
        {

            try {
                  Map <String,Object> checkMap=checkObjFieldIsNull(businessUser);

                if(checkMap.get("flag").equals(false)){
                    map.put("msg", "成功导入"+addNum+"条,共"+totalNum+"条."+"第"+(addNum+1)+"条的"+checkMap.get("msg"));
                    return map;
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //把导入的日期进行格式化,
                    String stringOrderTime =sdf.format(businessUser.getOrderTime());    //转字符串
                    String stringCallTime =sdf.format(businessUser.getCallTime());      //转字符串
                    Date revertOrderTime=sdf.parse(stringOrderTime);                  //转date
                    Date revertCallTime=sdf.parse(stringCallTime);                    //转date
                    businessUser.setOrderTime(revertOrderTime);
                    businessUser.setCallTime(revertCallTime);

                    int existUser = businessUserMapper.selectBusinessUserIfExists(businessUser);
                    if (existUser==0){
                        this.businessUserMapper.insertBusinessUser(businessUser);
                        addNum++;
                    }else{
                        map.put("msg", "成功导入"+addNum+"条,共"+totalNum+"条." +"第"+(addNum+1)+"条的"+"客户"+businessUser.getCustomerNum()+"在时间"+stringOrderTime+"购买的产品:"+businessUser.getProductName()+"的记录已存在,请勿重复添加!");
                        return map;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
                }
        map.put("msg", "成功导入"+addNum+"条,共"+totalNum+"条.");
        return map;
    }


    //是否该对象已存在
    @Override
    public int selectBusinessUserIfExists(BusinessUser businessUser) {
        return this.selectBusinessUserIfExists(businessUser);
    }

    @Override
    public List<List<String>> changeExcel(List<BusinessUser> lists) {
        List<List<String>> excelData = new ArrayList<List<String>>();

        List<String> head = new ArrayList<>();
        head.add("客户号码");
        head.add("省份");
        head.add("所属集团");
        head.add("产品ID");
        head.add("产品名称");
        head.add("所属CP");
        head.add("发展人");
        head.add("订购时间");
        head.add("话单时间");
        head.add("产品金额");
        head.add("返点比例");

        excelData.add(head);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < lists.size(); i++) {
            List<String> data = new ArrayList<>();
            data.add(lists.get(i).getCustomerNum().equals("")?"":lists.get(i).getCustomerNum());
            data.add(lists.get(i).getProvince().equals("")?"":lists.get(i).getProvince());
            data.add(lists.get(i).getMembership().equals("")?"":lists.get(i).getMembership());
            data.add(lists.get(i).getProductId().equals("")?"":lists.get(i).getProductId());
            data.add(lists.get(i).getProductName().equals("")?"":lists.get(i).getProductName());
            data.add(lists.get(i).getBelongCp().equals("")?"":lists.get(i).getBelongCp());
            data.add(lists.get(i).getDevelopeNum().equals("")?"":lists.get(i).getDevelopeNum());
            data.add(sdf.format(lists.get(i).getOrderTime()).equals("")?"":sdf.format(lists.get(i).getOrderTime()));
            data.add(sdf.format(lists.get(i).getCallTime()).equals("")?"":sdf.format(lists.get(i).getCallTime()));
            data.add(lists.get(i).getProductAssets().toString().equals("")?"":lists.get(i).getProductAssets().toString());
            data.add(lists.get(i).getProductRate().equals("")?"":lists.get(i).getProductRate());
            excelData.add(data);
        }
        return excelData;
    }

    @Override
    public List<BusinessUser> selectExportBusinessUserList(BusinessUser businessUser) {
        return businessUserMapper.selectExportBusinessUserList(businessUser);
    }

    /****
     * @param object 对象
     * @return 如果对象不为空，且没有空值。返回true，对象为空或者有空值，返回false
     * */
    private  Map<String,Object> checkObjFieldIsNull(Object object) throws IllegalAccessException{

        List<String> excludeNames = new ArrayList<String>();  //把不用验证非空的属性加入到excludeNameslist中
        excludeNames.add("developePerson");
        excludeNames.add("id");

        List<String> vertifyNames = new ArrayList<String>();  //把特别验证的属性加入到vertifyNames中
        vertifyNames.add("customerNum");
        vertifyNames.add("productAssets");
        vertifyNames.add("productRates");

        Map<String,Object> map=new HashMap<>();
        String revertName="";
        boolean flag = false;
        if(null!=object){
            for(Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true);                       //在用反射时访问私有变量（private修饰变量）
//              if(!excludeNames.contains(field.getName())){        //如果该字段不在排除验证的字段list中
                  if (field.isAnnotationPresent(Excel.class)){    //获取@Excel标注的注释名,也就是属性中文名
                      Excel mark = field.getAnnotation(Excel.class);
                      revertName = mark.name();
                      if(field.get(object) == null || field.get(object).toString().trim().equals("")){   //某个属性为空的情况
                          flag = false;
                          map.put("flag",flag);
                          map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                          return map;
                      }
                      if(vertifyNames.contains(field.getName())){
                          if(field.getName().equals("customerNum")){
                              boolean a= utils.isMobileNO(field.get(object).toString());
                              if(a==false){
                                  map.put("flag",false);
                                  map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                                  return map;
                              }
                          }
                          if(field.getName().equals("productAssets")){
                              boolean b= utils.verifyNum2(field.get(object).toString());
                              if(b==false){
                                  map.put("flag",false);
                                  map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                                  return map;
                              }
                              if(field.getName().equals("productRate")){
                                  boolean c= utils.verifyNum1(field.get(object).toString());
                                  if(c==false){
                                      map.put("flag",false);
                                      map.put("msg",revertName+"的数据格式不正确,请检查后重新上传!");
                                      return map;
                                  }
                              }
                          }
                      }
                  }

//              }
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



}

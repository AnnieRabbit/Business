package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.service.IBusinessUserService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户订单Controller
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/business/businessUser")
public class BusinessUserController extends BaseController
{
    private String prefix = "business/businessUser";

    @Autowired
    private IBusinessUserService businessUserService;

    @RequiresPermissions("business:businessUser:view")
    @GetMapping()
    public String businessUser()
    {
        return prefix + "/businessUser";
    }

    /**
     * 查询客户订单列表
     */
    @RequiresPermissions("business:businessUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessUser businessUser)
    {
        startPage();
        List<BusinessUser> list = businessUserService.selectBusinessUserList(businessUser);
        return getDataTable(list);
    }

    /**
     * 导出客户订单
     */
    @RequiresPermissions("business:businessUser:export")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public void exportLog(HttpServletResponse response, BusinessUser businessUser) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();
        String sheetName = "客户订单";
        String fileName = "客户订单" + sdf.format(new Date()) + ".xls";
        List<BusinessUser> lists = businessUserService.selectExportBusinessUserList(businessUser);
        List<List<String>> excelData = businessUserService.changeExcel(lists);
        com.ruoyi.common.utils.ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);

    }
    /**
     * 导入客户订单excel
     */
    @RequiresPermissions("business:businessUser:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<BusinessUser> util = new ExcelUtil<BusinessUser>(BusinessUser.class);
        return util.importTemplateExcel("客户订单数据");
    }

    /**
     * 导入客户订单
     */
    @Log(title = "客户订单", businessType = BusinessType.IMPORT)
    @RequiresPermissions("business:businessUser:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws ParseException {
        ExcelUtil<BusinessUser> util = new ExcelUtil<BusinessUser>(BusinessUser.class);
        List<BusinessUser> userList = null;
        Map<String, Object> map=new HashMap<>();
        try {
            userList = util.importExcel(file.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("excel模板格式不正确,请重新选择!");
        }
        map = businessUserService.importBusinessUser(userList);
        return AjaxResult.success(""+map.get("msg"));
    }


    /**
     * 新增客户订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存客户订单
     */
    @RequiresPermissions("business:businessUser:add")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessUser businessUser)
    {
        int a=businessUserService.insertBusinessUser(businessUser);
        if(a==-1){
            return error("客户订单已存在,请勿重复添加");
        }else{
            return toAjax(a);
        }

    }

    /**
     * 修改客户订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusinessUser businessUser = businessUserService.selectBusinessUserById(id);
        mmap.put("businessUser", businessUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户订单
     */
    @RequiresPermissions("business:businessUser:edit")
    @Log(title = "客户订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessUser businessUser)
    {
            return toAjax(businessUserService.updateBusinessUser(businessUser));

    }

    /**
     * 删除客户订单
     */
//    @RequiresPermissions("business:businessUser:remove")
//    @Log(title = "客户订单", businessType = BusinessType.DELETE)
//    @PostMapping( "/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids)
//    {
//
//        return toAjax(businessUserService.deleteBusinessUserByIds(ids));
//    }


    /**
     * 删除客户订单
     */
    @RequiresPermissions("business:businessUser:remove")
    @Log(title = "客户订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        int a=businessUserService.deleteBusinessUserById(id);

        if(a==-1){
            return error("账单表中存在此客户订单的信息,不能删除!");
    }else{
            return toAjax(a);
        }
    }

}

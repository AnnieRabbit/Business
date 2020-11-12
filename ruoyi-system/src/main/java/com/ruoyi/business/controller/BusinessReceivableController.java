package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessReceivable;
import com.ruoyi.business.service.IBusinessReceivableService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应收账单Controller
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/business/businessReceivable")
public class BusinessReceivableController extends BaseController
{
    private String prefix = "business/businessReceivable";

    @Autowired
    private IBusinessReceivableService businessReceivableService;

    @RequiresPermissions("business:businessReceivable:view")
    @GetMapping()
    public String businessReceivable()
    {
        return prefix + "/businessReceivable";
    }

    /**
     * 查询应收账单列表
     */
    @RequiresPermissions("business:businessReceivable:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessReceivable businessReceivable)
    {
        startPage();
        List<BusinessReceivable> list = businessReceivableService.selectBusinessReceivableList(businessReceivable);
        return getDataTable(list);
    }

//    /**
//     * 导出应收账单列表
//     */
//    @RequiresPermissions("business:businessReceivable:export")
//    @Log(title = "应收账单", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(BusinessReceivable businessReceivable)
//    {
//        List<BusinessReceivable> list = businessReceivableService.selectBusinessReceivableList(businessReceivable);
//        ExcelUtil<BusinessReceivable> util = new ExcelUtil<BusinessReceivable>(BusinessReceivable.class);
//        return util.exportExcel(list, "businessReceivable");
//    }


    /**
     * 导出应收账单
     *
     * @param response
     * @param businessReceivable
     * @return
     * @throws IOException
     */
    @RequiresPermissions("business:businessReceivable:export")
    @Log(title = "应收账单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public void exportLog(HttpServletResponse response, BusinessReceivable businessReceivable) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();

        String sheetName = "应收账单";
        String fileName = "应收账单" + sdf.format(new Date()) + ".xls";
        List<BusinessReceivable> lists = businessReceivableService.selectExportBusinessReceivableList(businessReceivable);
        List<List<String>> excelData = businessReceivableService.changeExcel(lists);
        ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);

    }
    /**
     * 新增应收账单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存应收账单
     */
    @RequiresPermissions("business:businessReceivable:add")
    @Log(title = "应收账单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessReceivable businessReceivable)
    {
        int a= businessReceivableService.insertBusinessReceivable(businessReceivable);
        if(a==-1){
            return error("此客户订单不存在,请检查后重新添加");
        }
        else if(a==-2){
            return error("此应收收账单已存在,请勿重复添加");
        }else{
            return toAjax(a);
        }

    }

    /**
     * 修改应收账单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusinessReceivable businessReceivable = businessReceivableService.selectBusinessReceivableById(id);
        mmap.put("businessReceivable", businessReceivable);
        return prefix + "/edit";
    }

    /**
     * 修改保存应收账单
     */
    @RequiresPermissions("business:businessReceivable:edit")
    @Log(title = "应收账单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessReceivable businessReceivable)
    {
        int a=businessReceivableService.updateBusinessReceivable(businessReceivable);
        if(a==-2){
            return error("此应收账单已存在!");
        }else{
            return toAjax(a);
        }
    }

    /**
     * 删除应收账单
     */
    @RequiresPermissions("business:businessReceivable:remove")
    @Log(title = "应收账单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(businessReceivableService.deleteBusinessReceivableByIds(ids));
    }
}

package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessReceipts;
import com.ruoyi.business.service.IBusinessReceiptsService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实收账单Controller
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/business/businessReceipts")
public class BusinessReceiptsController extends BaseController
{
    private String prefix = "business/businessReceipts";

    @Autowired
    private IBusinessReceiptsService businessReceiptsService;

    @RequiresPermissions("business:businessReceipts:view")
    @GetMapping()
    public String businessReceipts()
    {
        return prefix + "/businessReceipts";
    }

    /**
     * 查询实收账单列表
     */
    @RequiresPermissions("business:businessReceipts:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessReceipts businessReceipts)
    {
        startPage();

        List<BusinessReceipts> list = businessReceiptsService.selectBusinessReceiptsList(businessReceipts);
        return getDataTable(list);
    }

    /**
     * 导出实收账单列表
     */
    @RequiresPermissions("business:businessReceipts:export")
    @Log(title = "实收账单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public void exportLog(HttpServletResponse response, BusinessReceipts businessReceipts) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();

        String sheetName = "实收账单";
        String fileName = "实收账单" + sdf.format(new Date()) + ".xls";
        List<BusinessReceipts> lists = businessReceiptsService.selectExportBusinessReceiptsList(businessReceipts);
        List<List<String>> excelData = businessReceiptsService.changeExcel(lists);
        com.ruoyi.common.utils.ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);

    }

    /**
     * 新增实收账单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存实收账单
     */
    @RequiresPermissions("business:businessReceipts:add")
    @Log(title = "实收账单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessReceipts businessReceipts)
    {
        int a=businessReceiptsService.insertBusinessReceipts(businessReceipts);
        if(a==-1){
            return error("此客户订单不存在,请检查后重新添加");
        }else if(a==-2){
            return error("此实收账单已存在,请勿重复添加");
        }
        else{
            return toAjax(a);
        }

    }

    /**
     * 修改实收账单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusinessReceipts businessReceipts = businessReceiptsService.selectBusinessReceiptsById(id);
        mmap.put("businessReceipts", businessReceipts);
        return prefix + "/edit";
    }

    /**
     * 修改保存实收账单
     */
    @RequiresPermissions("business:businessReceipts:edit")
    @Log(title = "实收账单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessReceipts businessReceipts)
    {
        int a=businessReceiptsService.updateBusinessReceipts(businessReceipts);
        if(a==-2){
            return error("此实收账单已存在!");
        }else{
            return toAjax(a);
        }

    }

    /**
     * 删除实收账单
     */
    @RequiresPermissions("business:businessReceipts:remove")
    @Log(title = "实收账单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(businessReceiptsService.deleteBusinessReceiptsByIds(ids));
    }

    /**
     * 计算账单
     */
    @PostMapping( "/count")
    @ResponseBody
    public AjaxResult countTotal(String inputDate,String developeNum){
        String convert=inputDate+"-01";
        Map <String,Object> map=  this.businessReceiptsService.countTotal(convert,developeNum);
        return AjaxResult.success(map);
    }
    /**
     * 导入excel模板
     */
    @RequiresPermissions("business:businessReceipts:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<BusinessReceipts> util = new ExcelUtil<BusinessReceipts>(BusinessReceipts.class);
        return util.importTemplateExcel("实收数据");
    }
    /**
     * 导入
     */
    @Log(title = "实收账单", businessType = BusinessType.IMPORT)
    @RequiresPermissions("business:businessReceipts:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file)
    {
        ExcelUtil<BusinessReceipts> util = new ExcelUtil<BusinessReceipts>(BusinessReceipts.class);
        List<BusinessReceipts> list = null;
        try {
            list = util.importExcel(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("excel模板格式不正确,请重新选择!");
        }
        Map<String, Object> map = businessReceiptsService.importBusinessReceipts(list);
        return AjaxResult.success(""+map.get("msg"));
    }


}


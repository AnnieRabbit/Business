package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessRate;
import com.ruoyi.business.service.IBusinessRateService;
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
 * 返点比例表Controller
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/business/businessRate")
public class BusinessRateController extends BaseController
{
    private String prefix = "business/businessRate";

    @Autowired
    private IBusinessRateService businessRateService;

    @RequiresPermissions("business:businessRate:view")
    @GetMapping()
    public String businessRate()
    {
        return prefix + "/businessRate";
    }

    /**
     * 查询返点比例表列表
     */
    @RequiresPermissions("business:businessRate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessRate businessRate)
    {
        startPage();
        List<BusinessRate> list = businessRateService.selectBusinessRateList(businessRate);
        return getDataTable(list);
    }

    /**
     * 导出返点比例表列表
     */
    @RequiresPermissions("business:businessRate:export")
    @Log(title = "集团返点比例", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public void exportLog(HttpServletResponse response, BusinessRate businessRate) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();

        String sheetName = "集团返点比例";
        String fileName = "集团返点比例" + sdf.format(new Date()) + ".xls";
        List<BusinessRate> lists = businessRateService.selectExportBusinessRateList(businessRate);
        List<List<String>> excelData = businessRateService.changeExcel(lists);
        ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);
    }

    /**
     * 新增返点比例表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存返点比例表
     */
    @RequiresPermissions("business:businessRate:add")
    @Log(title = "返点比例表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessRate businessRate)
    {
        int a=businessRateService.insertBusinessRate(businessRate);
            if(a==-1){
            return error("此集团返点比例已存在,请勿重复添加");
        }else{
            return toAjax(a);
        }

    }

    /**
     * 修改返点比例表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusinessRate businessRate = businessRateService.selectBusinessRateById(id);
        mmap.put("businessRate", businessRate);
        return prefix + "/edit";
    }

    /**
     * 修改保存返点比例表
     */
    @RequiresPermissions("business:businessRate:edit")
    @Log(title = "返点比例表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessRate businessRate)
    {
        return toAjax(businessRateService.updateBusinessRate(businessRate));
    }

    /**
     * 删除返点比例表
     */
    @RequiresPermissions("business:businessRate:remove")
    @Log(title = "返点比例表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(businessRateService.deleteBusinessRateByIds(ids));
    }
}

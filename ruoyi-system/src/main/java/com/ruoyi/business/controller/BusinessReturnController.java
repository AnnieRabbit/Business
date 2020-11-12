package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessReturn;
import com.ruoyi.business.service.IBusinessReturnService;
import com.ruoyi.business.service.IBusinessUserService;
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
 * 投诉退费Controller
 * 
 * @author ruoyi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/business/businessReturn")
public class BusinessReturnController extends BaseController
{
    private String prefix = "business/businessReturn";

    @Autowired
    private IBusinessReturnService businessReturnService;

    @Autowired
    private IBusinessUserService businessUserService;

    @RequiresPermissions("business:businessReturn:view")
    @GetMapping()
    public String businessReturn()
    {
        return prefix + "/businessReturn";
    }

    /**
     * 查询投诉退费列表
     */
    @RequiresPermissions("business:businessReturn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessReturn businessReturn)
    {
        startPage();
        List<BusinessReturn> list = businessReturnService.selectBusinessReturnList(businessReturn);
        return getDataTable(list);
    }


    /**
     * 导出投诉退费
     *
     * @param response
     * @param businessReturn
     * @return
     * @throws IOException
     */
    @RequiresPermissions("business:businessReturn:export")
    @Log(title = "投诉退费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public void exportLog(HttpServletResponse response, BusinessReturn businessReturn) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();

        String sheetName = "投诉退费";
        String fileName = "投诉退费_" + sdf.format(new Date()) + ".xls";
        List<BusinessReturn> lists = businessReturnService.selectExportBusinessReturnList(businessReturn);
        List<List<String>> excelData = businessReturnService.changeExcel(lists);
       ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);

    }


//    @RequiresPermissions("business:businessReturn:export")
//    @Log(title = "投诉退费", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(BusinessReturn businessReturn)
//    {
//        List<BusinessReturn> list = businessReturnService.selectBusinessReturnList(businessReturn);
//        ExcelUtil<BusinessReturn> util = new ExcelUtil<BusinessReturn>(BusinessReturn.class);
//        return util.exportExcel(list, "businessReturn");
//    }

    /**
     * 新增投诉退费
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存投诉退费
     */
    @RequiresPermissions("business:businessReturn:add")
    @Log(title = "投诉退费", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessReturn businessReturn)
    {
        int a=businessReturnService.insertBusinessReturn(businessReturn);
        if(a==-1){
            return error("此客户订单不存在,请检查后重新添加");
        }else{
            return toAjax(a);
        }
    }

    /**
     * 修改投诉退费
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusinessReturn businessReturn = businessReturnService.selectBusinessReturnById(id);
        mmap.put("businessReturn", businessReturn);
        return prefix + "/edit";
    }

    /**
     * 修改保存投诉退费
     */
    @RequiresPermissions("business:businessReturn:edit")
    @Log(title = "投诉退费", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessReturn businessReturn)
    {
        return toAjax(businessReturnService.updateBusinessReturn(businessReturn));
    }

    /**
     * 删除投诉退费
     */
    @RequiresPermissions("business:businessReturn:remove")
    @Log(title = "投诉退费", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(businessReturnService.deleteBusinessReturnByIds(ids));
    }
}

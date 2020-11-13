package com.ruoyi.business.controller;

import com.ruoyi.business.domain.BusinessRate;
import com.ruoyi.business.domain.BusinessUser;
import com.ruoyi.business.service.IBusinessUserService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RequestMapping("/business/businessDevelope")
public class BusinessDevelopeController extends BaseController
{
    private String prefix = "business/businessDevelope";

    @Autowired
    private IBusinessUserService businessUserService;

    @RequiresPermissions("business:businessDevelope:view")
    @GetMapping()
    public String businessDevelope()
    {

        return prefix + "/businessDevelope";
    }

    /**
     * 发展人list
     * @RequestParam(defaultValue = "2021-02-01")
     */

    @RequiresPermissions("business:businessDevelope:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list( String inputDate, String developeNum)
    {
        String convert=inputDate+"-01";
        startPage();
        List<BusinessUser> list = businessUserService.countList(convert,developeNum);
        return getDataTable(list);
    }


    /**
     * 导出发展人账单(按日期)
     */
    @RequiresPermissions("business:businessDevelope:export")
    @Log(title = "发展人账单总览", businessType = BusinessType.EXPORT)
    @GetMapping("/export/{inputDate}")
    @ResponseBody
    public void exportLog(HttpServletResponse response,@PathVariable("inputDate") String inputDate, String developeNum) throws IOException {
        String convert=inputDate+"-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();

        String sheetName = "发展人账单总览";
        String fileName = "发展人账单总览_" + sdf.format(new Date()) + ".xls";
        List<BusinessUser> lists = businessUserService.countList(convert,developeNum);
        List<List<String>> excelData = businessUserService.changeExcelDevelope(lists);
        ExcelUtil.exportExcel(response, excelData, sheetName, fileName, 15);
    }

}

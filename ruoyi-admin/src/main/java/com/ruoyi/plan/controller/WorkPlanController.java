package com.ruoyi.plan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.poi.POIUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.plan.domain.WorkPlan;
import com.ruoyi.plan.service.IWorkPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工作计划导入Controller
 *
 * @author ruoyi
 * @date 2021-07-30
 */
@RestController
@RequestMapping("/plan/import")
public class WorkPlanController extends BaseController
{
    @Autowired
    private IWorkPlanService workPlanService;

    /**
     * 查询工作计划导入列表
     */
    @PreAuthorize("@ss.hasPermi('plan:import:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkPlan workPlan)
    {
        startPage();
        List<WorkPlan> list = workPlanService.selectWorkPlanList(workPlan);
        return getDataTable(list);
    }

    /**
     * 导出工作计划导入列表
     */
    @PreAuthorize("@ss.hasPermi('plan:import:export')")
    @Log(title = "工作计划导入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WorkPlan workPlan)
    {
        List<WorkPlan> list = workPlanService.selectWorkPlanList(workPlan);
        ExcelUtil<WorkPlan> util = new ExcelUtil<WorkPlan>(WorkPlan.class);
        return util.exportExcel(list, "工作计划导入数据");
    }

    /**
     * 获取工作计划导入详细信息
     */
    @PreAuthorize("@ss.hasPermi('plan:import:query')")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") Long uuid)
    {
        return AjaxResult.success(workPlanService.selectWorkPlanById(uuid));
    }

    /**
     * 新增工作计划导入
     */
    @PreAuthorize("@ss.hasPermi('plan:import:add')")
    @Log(title = "工作计划导入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkPlan workPlan)
    {
        return toAjax(workPlanService.insertWorkPlan(workPlan));
    }

    /**
     * 修改工作计划导入
     */
    @PreAuthorize("@ss.hasPermi('plan:import:edit')")
    @Log(title = "工作计划导入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkPlan workPlan)
    {
        return toAjax(workPlanService.updateWorkPlan(workPlan));
    }

    /**
     * 删除工作计划导入
     */
    @PreAuthorize("@ss.hasPermi('plan:import:remove')")
    @Log(title = "工作计划导入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable Long[] uuids)
    {
        return toAjax(workPlanService.deleteWorkPlanByIds(uuids));
    }

    /**
     * 导入工作计划
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file)
    {
        XSSFWorkbook wb = null;
        try
        {
            wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);

            List<Object[]> rows = new ArrayList();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)
            {
                Row row = sheet.getRow(i);
                List rowData = new ArrayList();
                for (int j = 0; j <= 7; j++)
                {
                    rowData.add(POIUtil.getCellValue(row, j));
                }
                rows.add(rowData.toArray());
            }

            workPlanService.saveImportData(rows);
            return success();
        }
        catch (CustomException e)
        {
            return AjaxResult.error(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error();
        }
        finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}

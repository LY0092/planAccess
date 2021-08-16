package com.ruoyi.plan.controller;

import java.util.List;

import com.ruoyi.plan.domain.PlanSplit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.plan.domain.PlanCycle;
import com.ruoyi.plan.service.IPlanCycleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划分解Controller
 *
 * @author ruoyi
 * @date 2021-07-31
 */
@RestController
@RequestMapping("/plan/split")
public class PlanCycleController extends BaseController
{
    @Autowired
    private IPlanCycleService planCycleService;

    /**
     * 查询计划分解列表
     */
    @PreAuthorize("@ss.hasPermi('plan:split:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanCycle planCycle)
    {
        startPage();
        List<PlanCycle> list = planCycleService.selectPlanCycleList(planCycle);
        return getDataTable(list);
    }

    /**
     * 导出计划分解列表
     */
    @PreAuthorize("@ss.hasPermi('plan:split:export')")
    @Log(title = "计划分解", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PlanCycle planCycle)
    {
        List<PlanCycle> list = planCycleService.selectPlanCycleList(planCycle);
        ExcelUtil<PlanCycle> util = new ExcelUtil<PlanCycle>(PlanCycle.class);
        return util.exportExcel(list, "计划分解数据");
    }

    /**
     * 获取计划分解详细信息
     */
    @PreAuthorize("@ss.hasPermi('plan:split:query')")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") Long uuid)
    {
        return AjaxResult.success(planCycleService.selectPlanCycleById(uuid));
    }

    /**
     * 新增计划分解
     */
    @PreAuthorize("@ss.hasPermi('plan:split:add')")
    @Log(title = "计划分解", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanCycle planCycle)
    {
        return toAjax(planCycleService.insertPlanCycle(planCycle));
    }

    /**
     * 修改计划分解
     */
    @PreAuthorize("@ss.hasPermi('plan:split:edit')")
    @Log(title = "计划分解", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanCycle planCycle)
    {
        return toAjax(planCycleService.updatePlanCycle(planCycle));
    }

    /**
     * 删除计划分解
     */
    @PreAuthorize("@ss.hasPermi('plan:split:remove')")
    @Log(title = "计划分解", businessType = BusinessType.DELETE)
    @DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable Long[] uuids)
    {
        return toAjax(planCycleService.deletePlanCycleByIds(uuids));
    }

    /**
     * 计划分解
     *
     * @param planCycle
     * @return
     */
    @PostMapping("/planSplit")
    public AjaxResult planSplit(@RequestBody PlanCycle planCycle)
    {
        planCycleService.planSplit(planCycle.getUuid());
        return success();
    }

    /**
     * 撤销分解
     *
     * @param planCycle
     * @return
     */
    @PostMapping("/unPlanSplit")
    public AjaxResult unPlanSplit(@RequestBody PlanCycle planCycle)
    {
        planCycleService.unPlanSplit(planCycle.getUuid());
        return success();
    }

    /**
     * 查询计划分解明细
     *
     * @param planCycle
     * @return
     */
    @PostMapping("/splitList")
    public TableDataInfo findPlanSplitList(@RequestBody PlanCycle planCycle)
    {
        startPage();
        List<PlanSplit> list = planCycleService.findPlanSplitList(planCycle.getUuid());
        return getDataTable(list);
    }
}

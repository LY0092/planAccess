package com.ruoyi.plan.controller;

import java.util.List;

import com.ruoyi.plan.domain.PlanCycle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.plan.domain.AccessCycle;
import com.ruoyi.plan.service.IAccessCycleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核周期Controller
 *
 * @author ruoyi
 * @date 2021-08-06
 */
@RestController
@RequestMapping("/plan/access")
public class AccessCycleController extends BaseController
{
    @Autowired
    private IAccessCycleService accessCycleService;

    /**
     * 查询考核周期列表
     */
    @PreAuthorize("@ss.hasPermi('plan:access:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccessCycle accessCycle)
    {
        startPage();
        List<AccessCycle> list = accessCycleService.selectAccessCycleList(accessCycle);
        return getDataTable(list);
    }

    /**
     * 导出考核周期列表
     */
    @PreAuthorize("@ss.hasPermi('plan:access:export')")
    @Log(title = "考核周期", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccessCycle accessCycle)
    {
        List<AccessCycle> list = accessCycleService.selectAccessCycleList(accessCycle);
        ExcelUtil<AccessCycle> util = new ExcelUtil<AccessCycle>(AccessCycle.class);
        return util.exportExcel(list, "考核周期数据");
    }

    /**
     * 获取考核周期详细信息
     */
    @PreAuthorize("@ss.hasPermi('plan:access:query')")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") Long uuid)
    {
        return AjaxResult.success(accessCycleService.selectAccessCycleById(uuid));
    }

    /**
     * 新增考核周期
     */
    @PreAuthorize("@ss.hasPermi('plan:access:add')")
    @Log(title = "考核周期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccessCycle accessCycle)
    {
        return toAjax(accessCycleService.insertAccessCycle(accessCycle));
    }

    /**
     * 修改考核周期
     */
    @PreAuthorize("@ss.hasPermi('plan:access:edit')")
    @Log(title = "考核周期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccessCycle accessCycle)
    {
        return toAjax(accessCycleService.updateAccessCycle(accessCycle));
    }

    /**
     * 删除考核周期
     */
    @PreAuthorize("@ss.hasPermi('plan:access:remove')")
    @Log(title = "考核周期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable Long[] uuids)
    {
        return toAjax(accessCycleService.deleteAccessCycleByIds(uuids));
    }

    /**
     * 上报目标考核
     *
     * @param accessCycle
     * @return
     */
    @PostMapping("/pushPlanCycle")
    public AjaxResult pushPlanCycle(@RequestBody AccessCycle accessCycle)
    {
        accessCycleService.pushPlanCycle(accessCycle.getUuid());
        return success();
    }

    /**
     *关闭计划上报
     *
     * @param accessCycle
     * @return
     */
    @PostMapping("/closePush")
    public AjaxResult closePush(@RequestBody AccessCycle accessCycle)
    {
        accessCycleService.closePush(accessCycle.getUuid());
        return success();
    }

    /**
     * 关闭领导审核
     *
     * @param accessCycle
     * @return
     */
    @PostMapping("/closeApprove")
    public AjaxResult closeApprove(@RequestBody AccessCycle accessCycle)
    {
        accessCycleService.closeApprove(accessCycle.getUuid());
        return success();
    }

    /**
     * 关闭附件上传
     *
     * @param accessCycle
     * @return
     */
    @PostMapping("/closeUpload")
    public AjaxResult closeUpload(@RequestBody AccessCycle accessCycle)
    {
        accessCycleService.closeUpload(accessCycle.getUuid());
        return success();
    }

    /**
     * 关闭领导确认
     *
     * @param accessCycle
     * @return
     */
    @PostMapping("/closeConfirm")
    public AjaxResult closeConfirm(@RequestBody AccessCycle accessCycle)
    {
        accessCycleService.closeConfirm(accessCycle.getUuid());
        return success();
    }
}

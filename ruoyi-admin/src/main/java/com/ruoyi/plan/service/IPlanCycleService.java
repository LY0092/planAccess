package com.ruoyi.plan.service;

import java.util.List;
import com.ruoyi.plan.domain.PlanCycle;
import com.ruoyi.plan.domain.PlanSplit;

/**
 * 计划分解Service接口
 *
 * @author ruoyi
 * @date 2021-07-31
 */
public interface IPlanCycleService
{
    /**
     * 查询计划分解
     *
     * @param uuid 计划分解ID
     * @return 计划分解
     */
    public PlanCycle selectPlanCycleById(Long uuid);

    /**
     * 查询计划分解列表
     *
     * @param planCycle 计划分解
     * @return 计划分解集合
     */
    public List<PlanCycle> selectPlanCycleList(PlanCycle planCycle);

    /**
     * 新增计划分解
     *
     * @param planCycle 计划分解
     * @return 结果
     */
    public int insertPlanCycle(PlanCycle planCycle);

    /**
     * 修改计划分解
     *
     * @param planCycle 计划分解
     * @return 结果
     */
    public int updatePlanCycle(PlanCycle planCycle);

    /**
     * 批量删除计划分解
     *
     * @param uuids 需要删除的计划分解ID
     * @return 结果
     */
    public int deletePlanCycleByIds(Long[] uuids);

    /**
     * 删除计划分解信息
     *
     * @param uuid 计划分解ID
     * @return 结果
     */
    public int deletePlanCycleById(Long uuid);

    /**
     * 计划分解
     * @param cycleId
     * @return
     */
    void planSplit(Long cycleId);


    /**
     * 查询计划分解明细
     * @param cycleId
     * @return
     */
    List<PlanSplit> findPlanSplitList(Long cycleId);


    /**
     * 撤销分解
     * @param cycleId
     * @return
     */
    void unPlanSplit(Long cycleId);
}

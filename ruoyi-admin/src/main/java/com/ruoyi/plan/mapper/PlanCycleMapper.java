package com.ruoyi.plan.mapper;

import java.util.List;
import com.ruoyi.plan.domain.PlanCycle;

/**
 * 计划分解Mapper接口
 * 
 * @author ruoyi
 * @date 2021-07-31
 */
public interface PlanCycleMapper 
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
     * 删除计划分解
     * 
     * @param uuid 计划分解ID
     * @return 结果
     */
    public int deletePlanCycleById(Long uuid);

    /**
     * 批量删除计划分解
     * 
     * @param uuids 需要删除的数据ID
     * @return 结果
     */
    public int deletePlanCycleByIds(Long[] uuids);
}

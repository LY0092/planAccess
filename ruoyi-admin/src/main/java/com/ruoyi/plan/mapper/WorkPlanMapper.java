package com.ruoyi.plan.mapper;

import java.util.List;
import com.ruoyi.plan.domain.WorkPlan;

/**
 * 工作计划导入Mapper接口
 * 
 * @author ruoyi
 * @date 2021-07-30
 */
public interface WorkPlanMapper 
{
    /**
     * 查询工作计划导入
     * 
     * @param uuid 工作计划导入ID
     * @return 工作计划导入
     */
    public WorkPlan selectWorkPlanById(Long uuid);

    /**
     * 查询工作计划导入列表
     * 
     * @param workPlan 工作计划导入
     * @return 工作计划导入集合
     */
    public List<WorkPlan> selectWorkPlanList(WorkPlan workPlan);

    /**
     * 新增工作计划导入
     * 
     * @param workPlan 工作计划导入
     * @return 结果
     */
    public int insertWorkPlan(WorkPlan workPlan);

    /**
     * 修改工作计划导入
     * 
     * @param workPlan 工作计划导入
     * @return 结果
     */
    public int updateWorkPlan(WorkPlan workPlan);

    /**
     * 删除工作计划导入
     * 
     * @param uuid 工作计划导入ID
     * @return 结果
     */
    public int deleteWorkPlanById(Long uuid);

    /**
     * 批量删除工作计划导入
     * 
     * @param uuids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkPlanByIds(Long[] uuids);
}

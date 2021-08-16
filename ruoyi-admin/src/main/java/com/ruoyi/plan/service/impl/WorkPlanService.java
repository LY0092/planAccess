package com.ruoyi.plan.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.plan.mapper.WorkPlanMapper;
import com.ruoyi.plan.domain.WorkPlan;
import com.ruoyi.plan.service.IWorkPlanService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作计划导入Service业务层处理
 *
 * @author ruoyi
 * @date 2021-07-30
 */
@Service
public class WorkPlanService implements IWorkPlanService
{
    @Autowired
    private WorkPlanMapper workPlanMapper;

    /**
     * 查询工作计划导入
     *
     * @param uuid 工作计划导入ID
     * @return 工作计划导入
     */
    @Override
    public WorkPlan selectWorkPlanById(Long uuid)
    {
        return workPlanMapper.selectWorkPlanById(uuid);
    }

    /**
     * 查询工作计划导入列表
     *
     * @param workPlan 工作计划导入
     * @return 工作计划导入
     */
    @Override
    public List<WorkPlan> selectWorkPlanList(WorkPlan workPlan)
    {
        return workPlanMapper.selectWorkPlanList(workPlan);
    }

    /**
     * 新增工作计划导入
     *
     * @param workPlan 工作计划导入
     * @return 结果
     */
    @Override
    public int insertWorkPlan(WorkPlan workPlan)
    {
        return workPlanMapper.insertWorkPlan(workPlan);
    }

    /**
     * 修改工作计划导入
     *
     * @param workPlan 工作计划导入
     * @return 结果
     */
    @Override
    public int updateWorkPlan(WorkPlan workPlan)
    {
        return workPlanMapper.updateWorkPlan(workPlan);
    }

    /**
     * 批量删除工作计划导入
     *
     * @param uuids 需要删除的工作计划导入ID
     * @return 结果
     */
    @Override
    public int deleteWorkPlanByIds(Long[] uuids)
    {
        return workPlanMapper.deleteWorkPlanByIds(uuids);
    }

    /**
     * 删除工作计划导入信息
     *
     * @param uuid 工作计划导入ID
     * @return 结果
     */
    @Override
    public int deleteWorkPlanById(Long uuid)
    {
        return workPlanMapper.deleteWorkPlanById(uuid);
    }

    /**
     * 保存导入工作计划导入信息
     *
     * @param rows
     */
    @Transactional
    public void saveImportData(List<Object[]> rows)
    {
        for (Object[] obj : rows)
        {
            WorkPlan workPlan = new WorkPlan();
            workPlan.setDimension((String) obj[0]);
            workPlan.setPlanName((String) obj[1]);
            workPlan.setDeptPerson((String) obj[2]);
            if (obj[3] instanceof Date)
            {
                workPlan.setStartTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, (Date) obj[3]));
            }
            else
            {
                workPlan.setStartTime(String.valueOf(obj[3]));
            }
            if (obj[4] instanceof Date)
            {
                workPlan.setEndTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, (Date) obj[4]));
            }
            else
            {
                workPlan.setStartTime(String.valueOf(obj[4]));
            }

            // 计划名称为空不保存
            String planName = workPlan.getPlanName();
            if(StringUtils.isEmpty(planName)){
                continue;
            }

            workPlan.setGainDesc((String) obj[5]);
            workPlan.setPlanYear((String) obj[6]);
            workPlan.setRemark((String) obj[7]);
            workPlanMapper.insertWorkPlan(workPlan);
        }
    }
}

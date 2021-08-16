package com.ruoyi.plan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.jpa.EntityDao;
import com.ruoyi.plan.domain.BusUser;
import com.ruoyi.plan.domain.PlanSplit;
import com.ruoyi.plan.domain.WorkPlan;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.plan.mapper.PlanCycleMapper;
import com.ruoyi.plan.domain.PlanCycle;
import com.ruoyi.plan.service.IPlanCycleService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计划分解Service业务层处理
 *
 * @author ruoyi
 * @date 2021-07-31
 */
@Service
public class PlanCycleService implements IPlanCycleService
{
    @Autowired
    private PlanCycleMapper planCycleMapper;

    @Autowired
    private EntityDao entityDao;

    /**
     * 查询计划分解
     *
     * @param uuid 计划分解ID
     * @return 计划分解
     */
    @Override
    public PlanCycle selectPlanCycleById(Long uuid)
    {
        return planCycleMapper.selectPlanCycleById(uuid);
    }

    /**
     * 查询计划分解列表
     *
     * @param planCycle 计划分解
     * @return 计划分解
     */
    @Override
    public List<PlanCycle> selectPlanCycleList(PlanCycle planCycle)
    {
        return planCycleMapper.selectPlanCycleList(planCycle);
    }

    /**
     * 新增计划分解
     *
     * @param planCycle 计划分解
     * @return 结果
     */
    @Override
    public int insertPlanCycle(PlanCycle planCycle)
    {
        planCycle.setYear(planCycle.getMonth().substring(0, 4));
        return planCycleMapper.insertPlanCycle(planCycle);
    }

    /**
     * 修改计划分解
     *
     * @param planCycle 计划分解
     * @return 结果
     */
    @Override
    public int updatePlanCycle(PlanCycle planCycle)
    {
        planCycle.setYear(planCycle.getMonth().substring(0, 4));
        return planCycleMapper.updatePlanCycle(planCycle);
    }

    /**
     * 批量删除计划分解
     *
     * @param uuids 需要删除的计划分解ID
     * @return 结果
     */
    @Override
    public int deletePlanCycleByIds(Long[] uuids)
    {
        return planCycleMapper.deletePlanCycleByIds(uuids);
    }

    /**
     * 删除计划分解信息
     *
     * @param uuid 计划分解ID
     * @return 结果
     */
    @Override
    public int deletePlanCycleById(Long uuid)
    {
        return planCycleMapper.deletePlanCycleById(uuid);
    }

    /**
     * 计划分解
     *
     * @param cycleId
     * @return
     */
    @Transactional
    public void planSplit(Long cycleId)
    {
        // 查询导入的计划
        PlanCycle cycle = entityDao.get(PlanCycle.class, cycleId);
        Criteria criteria = entityDao.getCriteria(WorkPlan.class);
        criteria.add(Restrictions.le("endTime", cycle.getEndTime()));
        criteria.add(Restrictions.ge("endTime", cycle.getStartTime()));
        List<WorkPlan> planRows = criteria.list();

        if (planRows.isEmpty())
        {
            throw new CustomException("没有需要分解的计划");
        }

        // 先把用户部门查出来
        criteria = entityDao.getCriteria(BusUser.class);
        List<BusUser> userRows = criteria.list();
        Map codeMap = new HashMap();
        for (BusUser user : userRows)
        {
            codeMap.put(user.getUserName(), user.getDeptId());
        }

        // 分解计划
        for (WorkPlan plan : planRows)
        {
            String deptCode = plan.getDeptPerson();
            if (StringUtils.isEmpty(deptCode))
            {
                continue;
            }

            String[] codes = deptCode.split(",");
            for (String code : codes)
            {
                // 部门编码这里是用户帐号， 用户帐号对应的部门ID
                Long deptId = (Long) codeMap.get(code);
                if (deptId == null)
                {
                    throw new CustomException("部门编码[" + code + "]对应的部门不存在");
                }

                // 这里保存到分解表
                PlanSplit planSplit = new PlanSplit();
                planSplit.setDeptId(deptId);
                planSplit.setDeptCode(code);
                planSplit.setCycleId(cycleId);
                planSplit.setDimension(plan.getDimension());
                planSplit.setStartTime(plan.getStartTime());
                planSplit.setEndTime(plan.getEndTime());
                planSplit.setPlanYear(plan.getPlanYear());
                planSplit.setPlanName(plan.getPlanName());
                planSplit.setGainDesc(plan.getGainDesc());
                planSplit.setRemark(plan.getRemark());
                entityDao.save(planSplit);
            }
        }

        // 更新周期为已分解
        cycle.setStatus("1");
        entityDao.update(cycle);
    }

    /**
     * 查询计划分解明细
     *
     * @param cycleId
     * @return
     */
    public List<PlanSplit> findPlanSplitList(Long cycleId)
    {
        Criteria criteria = entityDao.getCriteria(PlanSplit.class);
        criteria.add(Restrictions.eq("cycleId", cycleId));
        return criteria.list();
    }

    /**
     * 撤销分解
     *
     * @param cycleId
     * @return
     */
    @Transactional
    public void unPlanSplit(Long cycleId)
    {
        Criteria criteria = entityDao.getCriteria(PlanSplit.class);
        criteria.add(Restrictions.eq("cycleId", cycleId));
        entityDao.deleteAll(criteria.list());

        PlanCycle cycle = entityDao.get(PlanCycle.class, cycleId);
        cycle.setStatus("0");
        entityDao.update(cycle);
    }
}

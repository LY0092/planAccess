package com.ruoyi.plan.service.impl;

import java.util.*;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.MapDataUtil;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.constant.BusiConstants;
import com.ruoyi.framework.jpa.EntityDao;
import com.ruoyi.plan.domain.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.plan.mapper.AccessCycleMapper;
import com.ruoyi.plan.service.IAccessCycleService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 考核周期Service业务层处理
 *
 * @author ruoyi
 * @date 2021-08-06
 */
@Service
public class AccessCycleService implements IAccessCycleService
{
    @Autowired
    private AccessCycleMapper accessCycleMapper;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 查询考核周期
     *
     * @param uuid 考核周期ID
     * @return 考核周期
     */
    @Override
    public AccessCycle selectAccessCycleById(Long uuid)
    {
        return accessCycleMapper.selectAccessCycleById(uuid);
    }

    /**
     * 查询考核周期列表
     *
     * @param accessCycle 考核周期
     * @return 考核周期
     */
    @Override
    public List<AccessCycle> selectAccessCycleList(AccessCycle accessCycle)
    {
        return accessCycleMapper.selectAccessCycleList(accessCycle);
    }

    /**
     * 新增考核周期
     *
     * @param accessCycle 考核周期
     * @return 结果
     */
    @Override
    public int insertAccessCycle(AccessCycle accessCycle)
    {
        return accessCycleMapper.insertAccessCycle(accessCycle);
    }

    /**
     * 修改考核周期
     *
     * @param accessCycle 考核周期
     * @return 结果
     */
    @Override
    public int updateAccessCycle(AccessCycle accessCycle)
    {
        return accessCycleMapper.updateAccessCycle(accessCycle);
    }

    /**
     * 批量删除考核周期
     *
     * @param uuids 需要删除的考核周期ID
     * @return 结果
     */
    @Override
    public int deleteAccessCycleByIds(Long[] uuids)
    {
        return accessCycleMapper.deleteAccessCycleByIds(uuids);
    }

    /**
     * 删除考核周期信息
     *
     * @param uuid 考核周期ID
     * @return 结果
     */
    @Override
    public int deleteAccessCycleById(Long uuid)
    {
        return accessCycleMapper.deleteAccessCycleById(uuid);
    }

    /**
     * 上报目标考核
     *
     * @param uuid
     * @return
     */
    @Transactional
    public void pushPlanCycle(Long uuid)
    {
        AccessCycle cycle = entityDao.get(AccessCycle.class, uuid);
        String month = cycle.getMonth();

        Criteria criteria = entityDao.getCriteria(PlanCycle.class);
        criteria.add(Restrictions.eq("month", month));
        criteria.add(Restrictions.eq("type", "1"));
        List<PlanCycle> planCycles = criteria.list();

        if (planCycles.isEmpty())
        {
            throw new CustomException("[" + month + "]部门周期计划未分解");
        }

        PlanCycle planCycle = planCycles.get(0);
        String status = planCycle.getStatus();
        if ("0".equals(status))
        {
            throw new CustomException("[" + month + "]部门周期计划未分解");
        }

        // 查询分解明细
        Long cycleId = planCycle.getUuid();
        criteria = entityDao.getCriteria(PlanSplit.class);
        criteria.add(Restrictions.eq("cycleId", cycleId));
        List<PlanSplit> splitRows = criteria.list();

        // 计划明细
        Set<String> codeSet = new HashSet();
        for (PlanSplit planSplit : splitRows)
        {
            codeSet.add(planSplit.getDeptCode());
        }

        // 执行流程
        for (String code : codeSet)
        {
            // 查询部门用户ID和部门负责人ID
            criteria = entityDao.getCriteria(BusUser.class);
            criteria.add(Restrictions.eq("userName", code));
            List<BusUser> users = criteria.list();
            if (users.isEmpty())
            {
                continue;
            }
            Long userId = users.get(0).getUserId();
            Long deptId = users.get(0).getDeptId();
            if (deptId == null)
            {
                throw new CustomException("用户[" + users.get(0).getUserName() + "]未设置部门");
            }

            // 查询部门用户ID和部门负责人ID
            BusDept dept = entityDao.get(BusDept.class, deptId);
            if (dept.getLeader() == null)
            {
                throw new CustomException("部门[" + dept.getDeptName() + "]未设置部门负责人");
            }
            Long leaderId = Long.valueOf(dept.getLeader());

            // 发起流程
            String targetId = UUID.randomUUID().toString();
            Map paramMap = new HashMap();
            paramMap.put("dept", String.valueOf(userId));
            paramMap.put("leader", String.valueOf(leaderId));
            paramMap.put("jgb", String.valueOf(userId));
            paramMap.put("ldqr", String.valueOf(leaderId));
            String busKey = "dept_target";
            ProcessInstance pis = runtimeService.startProcessInstanceByKey(
                    busKey,
                    targetId,
                    paramMap);

            // 写入目标考核表
            PlanTarget planTarget = new PlanTarget();
            planTarget.setCycleId(cycle.getUuid());
            planTarget.setProcessId(pis.getId());
            planTarget.setUserId(userId);
            planTarget.setCreateTime(new Date());
            planTarget.setStatus("1");
            planTarget.setAppryStatus("1");
            planTarget.setTargetType("1");
            entityDao.save(planTarget);

            // 查询分解明细
            criteria = entityDao.getCriteria(PlanSplit.class);
            criteria.add(Restrictions.eq("cycleId", cycleId));
            criteria.add(Restrictions.eq("deptCode", code));
            List<PlanSplit> deptItems = criteria.list();

            // 保存分解明细
            for (PlanSplit planItem : deptItems)
            {
                PlanTargetItem item = new PlanTargetItem();
                item.setMonth(month);
                item.setTargetId(planTarget.getId());
                item.setEndTime(cycle.getEndTime());
                item.setDimension(planItem.getDimension());
                item.setGainDesc(planItem.getGainDesc());
                item.setWorkDesc(planItem.getPlanName());
                item.setItemType(BusiConstants.PLAN_ITEM_SPLIT);
                entityDao.save(item);
            }
        }

        // 更新上报状态
        cycle.setStatus("1");
        entityDao.update(cycle);
    }

    /**
     * 关闭计划上报
     *
     * @param uuid
     */
    @Transactional
    public void closePush(Long uuid)
    {
        Map paramMap = new HashMap();
        paramMap.put("cycleId", uuid);
        paramMap.put("status", "1");
        List<Map> rows = accessCycleMapper.selectTaskList(paramMap);
        rows = MapDataUtil.mapToCamelCase(rows);

        for (Map rowMap : rows)
        {
            String taskId = (String) rowMap.get("taskId");
            taskService.complete(taskId);

            Long targetId = (Long) rowMap.get("targetId");
            PlanTarget target = entityDao.get(PlanTarget.class, targetId);
            target.setAppryStatus("2");
            entityDao.update(target);
        }
    }

    /**
     * 关闭领导审核
     *
     * @param uuid
     */
    @Transactional
    public void closeApprove(Long uuid)
    {
        Map paramMap = new HashMap();
        paramMap.put("cycleId", uuid);
        paramMap.put("status", "2");
        List<Map> rows = accessCycleMapper.selectTaskList(paramMap);
        rows = MapDataUtil.mapToCamelCase(rows);

        for (Map rowMap : rows)
        {
            String taskId = (String) rowMap.get("taskId");
            taskService.complete(taskId);

            Long targetId = (Long) rowMap.get("targetId");
            PlanTarget target = entityDao.get(PlanTarget.class, targetId);
            target.setAppryStatus("3");
            entityDao.update(target);
        }
    }

    /**
     * 关闭附件上传
     *
     * @param uuid
     */
    @Transactional
    public void closeUpload(Long uuid)
    {
        Map paramMap = new HashMap();
        paramMap.put("cycleId", uuid);
        paramMap.put("status", "3");
        List<Map> rows = accessCycleMapper.selectTaskList(paramMap);
        rows = MapDataUtil.mapToCamelCase(rows);

        for (Map rowMap : rows)
        {
            // 查询是否上传附件
            Long targetId = (Long) rowMap.get("targetId");
            Criteria criteria = entityDao.getCriteria(PlanTargetItem.class);
            criteria.add(Restrictions.eq("targetId", targetId));
            List<PlanTargetItem> targetItems = criteria.list();

            for (PlanTargetItem targetItem : targetItems)
            {
                criteria = entityDao.getCriteria(TargetItemFile.class);
                criteria.add(Restrictions.eq("itemId", targetItem.getId()));
                List<TargetItemFile> files = criteria.list();
                if (files.isEmpty())
                {
                    targetItem.setScore(0d);
                    entityDao.update(targetItem);
                }
            }

            String taskId = (String) rowMap.get("taskId");
            taskService.complete(taskId);

            PlanTarget target = entityDao.get(PlanTarget.class, targetId);
            target.setAppryStatus("4");
            entityDao.update(target);
        }
    }

    /**
     * 关闭领导确认
     *
     * @param uuid
     */
    @Transactional
    public void closeConfirm(Long uuid)
    {
        Map paramMap = new HashMap();
        paramMap.put("cycleId", uuid);
        paramMap.put("status", "4");
        List<Map> rows = accessCycleMapper.selectTaskList(paramMap);
        rows = MapDataUtil.mapToCamelCase(rows);

        for (Map rowMap : rows)
        {
            String taskId = (String) rowMap.get("taskId");
            taskService.complete(taskId);

            Long targetId = (Long) rowMap.get("targetId");
            PlanTarget target = entityDao.get(PlanTarget.class, targetId);
            target.setAppryStatus("5");
            entityDao.update(target);
        }
    }
}

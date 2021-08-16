package com.ruoyi.plan.service.impl;

import com.ruoyi.common.utils.MapDataUtil;
import com.ruoyi.constant.BusiConstants;
import com.ruoyi.framework.jpa.EntityDao;
import com.ruoyi.plan.domain.PlanTarget;
import com.ruoyi.plan.domain.PlanTargetDTO;
import com.ruoyi.plan.domain.PlanTargetItem;
import com.ruoyi.plan.domain.TargetItemFile;
import com.ruoyi.plan.mapper.WorkTargetMapper;
import com.ruoyi.plan.service.IWorkTargetService;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作计划导入Service业务层处理
 *
 * @author ruoyi
 * @date 2021-05-28
 */
@Service
public class WorkTargetService implements IWorkTargetService
{

    @Autowired
    private WorkTargetMapper workTargetMapper;

    @Autowired
    private EntityDao entityDao;

    @Autowired
    private TaskService taskService;

    /**
     * 查询目标考核列表
     *
     * @param paramMap
     * @return
     */
    public List<Map> selectList(Map paramMap)
    {
        List rows = workTargetMapper.selectList(paramMap);
        return MapDataUtil.mapToCamelCase(rows);
    }


    /**
     * 查询目标考核项目列表
     *
     * @param paramMap
     * @return
     */
    public Map selectItemInfo(Long targetId)
    {
        // 查询考核周期
        Map paramMap = new HashMap();
        paramMap.put("targetId", targetId);
        List<Map> rows = workTargetMapper.selectItemInfo(paramMap);
        rows = MapDataUtil.mapToCamelCase(rows);
        Map infoMap = rows.get(0);

        // 查询分解的项目
        Criteria criteria = entityDao.getCriteria(PlanTargetItem.class);
        criteria.add(Restrictions.eq("targetId", targetId));
        criteria.add(Restrictions.eq("itemType", BusiConstants.PLAN_ITEM_SPLIT));
        criteria.addOrder(Order.asc("id"));
        infoMap.put("splitItem", criteria.list());

        // 查询新增的项目
        criteria = entityDao.getCriteria(PlanTargetItem.class);
        criteria.add(Restrictions.eq("targetId", targetId));
        criteria.add(Restrictions.eq("itemType", BusiConstants.PLAN_ITEM_ADD));
        criteria.addOrder(Order.asc("id"));
        infoMap.put("addItem", criteria.list());
        return infoMap;
    }

    /**
     * 保存计划填报
     *
     * @param targetDTO
     * @return
     */
    @Transactional
    public void savePlanInfo(PlanTargetDTO targetDTO)
    {
        // 更新分解的计划
        List<PlanTargetItem> splitRows = targetDTO.getSplitItem();
        for (PlanTargetItem targetItem : splitRows)
        {
            entityDao.update(targetItem);
        }

        // 更新新增的计划
        List<PlanTargetItem> addRows = targetDTO.getAddItem();
        for (PlanTargetItem targetItem : addRows)
        {
            if (targetItem.getId() == null)
            {
                entityDao.save(targetItem);
            }
            else
            {
                entityDao.update(targetItem);
            }
        }
    }

    /**
     * 提交计划填报
     *
     * @param targetDTO
     * @return
     */
    @Transactional
    public void submitPlanInfo(PlanTargetDTO targetDTO)
    {
        // 更新分解的计划
        List<PlanTargetItem> splitRows = targetDTO.getSplitItem();
        for (PlanTargetItem targetItem : splitRows)
        {
            entityDao.update(targetItem);
        }

        // 更新新增的计划
        List<PlanTargetItem> addRows = targetDTO.getAddItem();
        for (PlanTargetItem targetItem : addRows)
        {
            if (targetItem.getId() == null)
            {
                entityDao.save(targetItem);
            }
            else
            {
                entityDao.update(targetItem);
            }
        }

        // 更新状态
        Long tergetId = targetDTO.getTargetId();
        PlanTarget target = entityDao.get(PlanTarget.class, tergetId);
        String status = target.getAppryStatus();
        target.setAppryStatus(String.valueOf(Integer.valueOf(status) + 1));
        entityDao.update(target);

        // 审批流程
        taskService.complete(targetDTO.getTaskId());
    }

    /**
     * 保存附件
     *
     * @param itemFile
     */
    @Transactional
    public void saveTargetItemFile(TargetItemFile itemFile)
    {
        entityDao.save(itemFile);
    }

    /**
     * 查询附件列表
     *
     * @param itemId
     * @return
     */
    public List selectFileList(Long itemId)
    {
        Criteria criteria = entityDao.getCriteria(TargetItemFile.class);
        criteria.add(Restrictions.eq("itemId", itemId));
        criteria.addOrder(Order.asc("uploadTime"));
        return criteria.list();
    }


    /**
     * 删除附件
     *
     * @param id
     * @return
     */
    @Transactional
    public void deleteItemFile(Long id)
    {
        entityDao.delete(TargetItemFile.class, id);
    }
}

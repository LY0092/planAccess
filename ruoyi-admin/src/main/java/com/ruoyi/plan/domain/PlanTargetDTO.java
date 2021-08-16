package com.ruoyi.plan.domain;

import lombok.Data;

import java.util.List;

/**
 * 计划填报表单数据
 */
@Data
public class PlanTargetDTO
{
    private Long cycleId;
    private Long targetId;
    private String taskId;
    private String deptName;
    private String startTime;
    private String endTime;
    private String month;
    private String name;
    private String nickName;
    private String postName;

    private List<PlanTargetItem> splitItem;
    private List<PlanTargetItem> addItem;
}

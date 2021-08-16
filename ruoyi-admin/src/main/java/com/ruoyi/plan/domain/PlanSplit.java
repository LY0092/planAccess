package com.ruoyi.plan.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 工作计划分解对象 bus_plan_split
 *
 * @author ruoyi
 * @date 2021-06-04
 */
@Data
@Entity
@Table(name = "bus_plan_split")
public class PlanSplit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Long uuid;

    /**
     * 维度
     */
    @Column(name = "dimension")
    private String dimension;

    /**
     * 计划名称
     */
    @Column(name = "plan_name")
    private String planName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 成果物描述
     */
    @Column(name = "gain_desc")
    private String gainDesc;

    /**
     * 计划年度
     */
    @Column(name = "plan_year")
    private String planYear;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 周期ID
     */
    @Column(name = "cycle_id")
    private Long cycleId;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 部门编码
     */
    @Column(name = "dept_code")
    private String deptCode;
}

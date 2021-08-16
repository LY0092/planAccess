package com.ruoyi.plan.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 工作计划导入对象 bus_work_plan
 *
 * @author ruoyi
 * @date 2021-07-30
 */
@Data
@Entity
@Table(name = "bus_work_plan")
public class WorkPlan implements Serializable
{

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
     * 部门负责人
     */
    @Column(name = "dept_person")
    private String deptPerson;

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
}

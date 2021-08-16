package com.ruoyi.plan.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 计划分解对象 bus_plan_cycle
 *
 * @author ruoyi
 * @date 2021-07-31
 */
@Data
@Entity
@Table(name = "bus_plan_cycle")
public class PlanCycle implements Serializable
{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Long uuid;

    /**
     * 考核月份
     */
    @Column(name = "month")
    private String month;

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
     * 分解状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 周期类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 考核年份
     */
    @Column(name = "year")
    private String year;
}

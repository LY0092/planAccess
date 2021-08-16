package com.ruoyi.plan.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 考核周期对象 bus_access_cycle
*
* @author ruoyi
* @date 2021-06-04
*/
@Data
@Entity
@Table(name = "bus_access_cycle")
public class AccessCycle implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Long uuid;

    /** 月份 */
    @Column(name = "month")
    private String month;

    /** 周期名称 */
    @Column(name = "name")
    private String name;

    /** 周期类型 */
    @Column(name = "type")
    private String type;

    /** 绩效开始时间 */
    @Column(name = "start_time")
    private String startTime;

    /** 绩效结束时间 */
    @Column(name = "end_time")
    private String endTime;

    /** 周期状态 */
    @Column(name = "status")
    private String status;

    /** 周期说明 */
    @Column(name = "remark")
    private String remark;

    /** 部门编码 */
    @Column(name = "dept_id")
    private Long deptId;

    /** 部门名称 */
    @Transient
    private String deptName;
}

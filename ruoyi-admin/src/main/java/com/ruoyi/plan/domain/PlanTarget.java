package com.ruoyi.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 目标考核主对象 bus_plan_target
 *
 * @author ruoyi
 * @date 2021-06-29
 */
@Data
@Entity
@Table(name = "bus_plan_target")
public class PlanTarget implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 考核周期ID
     */
    @Column(name = "cycle_id")
    private Long cycleId;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 审批状态
     */
    @Column(name = "appry_status")
    private String appryStatus;

    /**
     * 总分
     */
    @Column(name = "score")
    private Double score;

    /**
     * 流程ID
     */
    @Column(name = "process_id")
    private String processId;

    /**
     * 考核人ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 目标类型
     */
    @Column(name = "target_type")
    private String targetType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

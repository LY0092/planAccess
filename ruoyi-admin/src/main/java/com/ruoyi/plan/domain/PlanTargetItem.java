package com.ruoyi.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 目标考核项目对象 bus_plan_target_item
 *
 * @author ruoyi
 * @date 2021-06-29
 */
@Data
@Entity
@Table(name = "bus_plan_target_item")
public class PlanTargetItem implements Serializable
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
     * 目标考核ID
     */
    @Column(name = "target_id")
    private Long targetId;

    /**
     * 考核月份
     */
    @Column(name = "month")
    private String month;

    /**
     * 维度
     */
    @Column(name = "dimension")
    private String dimension;

    /**
     * 责任人
     */
    @Column(name = "director")
    private String director;

    /**
     * 工作事项
     */
    @Column(name = "work_items")
    private String workItems;

    /**
     * 支付标准与成果
     */
    @Column(name = "gain_desc")
    private String gainDesc;

    /**
     * 工作内容
     */
    @Column(name = "work_desc")
    private String workDesc;

    /**
     * 完成时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 分值
     */
    @Column(name = "score")
    private Double score;

    /**
     * 项目类型
     */
    @Column(name = "item_type")
    private String itemType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

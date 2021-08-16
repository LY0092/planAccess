package com.ruoyi.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 目标考核项目附件对象 bus_target_item_file
 *
 * @author ruoyi
 * @date 2021-06-29
 */
@Data
@Entity
@Table(name = "bus_target_item_file")
public class TargetItemFile implements Serializable
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
     * 目标考核项目ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private String fileSize;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创造者
     */
    @Column(name = "creater")
    private String creater;
}

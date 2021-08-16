package com.ruoyi.plan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息对象 sys_user
 *
 * @author ruoyi
 * @date 2021-08-03
 */
@Data
@Entity
@Table(name = "sys_user")
public class BusUser implements Serializable
{

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Long deptId;

    /**
     * 用户账号
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phonenumber")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 头像地址
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Column(name = "status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Column(name = "login_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}

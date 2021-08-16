package com.ruoyi.plan.service;

import com.ruoyi.plan.domain.AccessCycle;

import java.util.List;

/**
 * 考核周期Service接口
 *
 * @author ruoyi
 * @date 2021-08-06
 */
public interface IAccessCycleService
{
    /**
     * 查询考核周期
     *
     * @param uuid 考核周期ID
     * @return 考核周期
     */
    public AccessCycle selectAccessCycleById(Long uuid);

    /**
     * 查询考核周期列表
     *
     * @param accessCycle 考核周期
     * @return 考核周期集合
     */
    public List<AccessCycle> selectAccessCycleList(AccessCycle accessCycle);

    /**
     * 新增考核周期
     *
     * @param accessCycle 考核周期
     * @return 结果
     */
    public int insertAccessCycle(AccessCycle accessCycle);

    /**
     * 修改考核周期
     *
     * @param accessCycle 考核周期
     * @return 结果
     */
    public int updateAccessCycle(AccessCycle accessCycle);

    /**
     * 批量删除考核周期
     *
     * @param uuids 需要删除的考核周期ID
     * @return 结果
     */
    public int deleteAccessCycleByIds(Long[] uuids);

    /**
     * 删除考核周期信息
     *
     * @param uuid 考核周期ID
     * @return 结果
     */
    public int deleteAccessCycleById(Long uuid);

    /**
     * 上报目标考核
     *
     * @param uuid
     * @return
     */
    void pushPlanCycle(Long uuid);

    /**
     * 关闭计划上报
     *
     * @param uuid
     */
    void closePush(Long uuid);

    /**
     * 关闭领导审核
     *
     * @param uuid
     */
    void closeApprove(Long uuid);

    /**
     * 关闭附件上传
     *
     * @param uuid
     */
    void closeUpload(Long uuid);

    /**
     * 关闭领导确认
     *
     * @param uuid
     */
    void closeConfirm(Long uuid);
}

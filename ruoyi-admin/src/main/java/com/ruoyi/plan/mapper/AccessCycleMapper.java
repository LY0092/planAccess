package com.ruoyi.plan.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.plan.domain.AccessCycle;

/**
 * 考核周期Mapper接口
 *
 * @author ruoyi
 * @date 2021-08-06
 */
public interface AccessCycleMapper
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
     * 删除考核周期
     *
     * @param uuid 考核周期ID
     * @return 结果
     */
    public int deleteAccessCycleById(Long uuid);

    /**
     * 批量删除考核周期
     *
     * @param uuids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccessCycleByIds(Long[] uuids);

    /**
     * 查询周期任务列表
     * @param paramMap
     * @return
     */
    List<Map> selectTaskList(Map paramMap);
}

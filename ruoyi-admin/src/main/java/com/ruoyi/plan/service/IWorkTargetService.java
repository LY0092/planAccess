package com.ruoyi.plan.service;

import com.ruoyi.plan.domain.PlanTargetDTO;
import com.ruoyi.plan.domain.TargetItemFile;

import java.util.List;
import java.util.Map;

/**
 * 目标考核Service
 *
 * @author ruoyi
 * @date 2021-05-28
 */
public interface IWorkTargetService
{
    /**
     * 查询目标考核列表
     *
     * @param paramMap
     * @return
     */
    List<Map> selectList(Map paramMap);


    /**
     * 查询目标考核项目列表
     *
     * @param targetId
     * @return
     */
    Map selectItemInfo(Long targetId);

    /**
     * 保存计划填报
     *
     * @param targetDTO
     * @return
     */
    void savePlanInfo(PlanTargetDTO targetDTO);

    /**
     * 提交计划填报
     *
     * @param targetDTO
     * @return
     */
    void submitPlanInfo(PlanTargetDTO targetDTO);

    /**
     * 保存符件
     *
     * @param itemFile
     */
    void saveTargetItemFile(TargetItemFile itemFile);

    /**
     * 查询附件列表
     *
     * @param itemId
     * @return
     */
    List selectFileList(Long itemId);


    /**
     * 删除附件
     *
     * @param id
     * @return
     */
    void deleteItemFile(Long id);
}

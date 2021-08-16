package com.ruoyi.plan.mapper;

import java.util.List;
import java.util.Map;

public interface WorkTargetMapper
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
     * @param paramMap
     * @return
     */
    List<Map> selectItemInfo(Map paramMap);
}

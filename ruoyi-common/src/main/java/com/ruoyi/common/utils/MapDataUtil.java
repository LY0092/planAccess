package com.ruoyi.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * Map通用处理方法
 *
 * @author ruoyi
 */
public class MapDataUtil
{
    /**
     * Map key下划线转驼峰
     *
     * @return
     */
    public static Map mapToCamelCase(Map map)
    {
        Map newMap = new HashMap();
        for (Object key : map.keySet())
        {
            if (key instanceof String)
            {
                String newKey = StringUtils.toCamelCase((String) key);
                newMap.put(newKey, map.get(key));
            }
            else
            {
                newMap.put(key, map.get(key));
            }
        }
        return newMap;
    }

    /**
     * Map key下划线转驼峰
     *
     * @return
     */
    public static List<Map> mapToCamelCase(List<Map> list)
    {
        List<Map> newList = new ArrayList<Map>();
        for (Map map : list)
        {
            newList.add(mapToCamelCase(map));
        }
        return newList;
    }


}

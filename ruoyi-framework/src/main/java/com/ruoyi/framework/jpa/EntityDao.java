package com.ruoyi.framework.jpa;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * JPA 实现接口
 */
@Component
public class EntityDao
{

    /**
     * 获得 Criteria 对象
     *
     * @param clazz
     * @return
     */
    public Criteria getCriteria(Class clazz)
    {
        return DetachedCriteria.forClass(clazz)
                .getExecutableCriteria(getSession());
    }

    /**
     * 获得JPA EntityManager
     *
     * @return
     */
    public EntityManager getEm()
    {
        return SpringUtils.getBean(EntityManager.class);
    }

    /**
     * 获得 Session 对象
     *
     * @return
     */
    public Session getSession()
    {
        return getEm().unwrap(Session.class);
    }

    /**
     * 保存对象
     *
     * @param object
     * @return
     */
    public Serializable save(Object object)
    {
        return getSession().save(object);
    }

    /**
     * 更新对象
     *
     * @param object
     * @return
     */
    public void update(Object object)
    {
        getSession().update(object);
    }

    /**
     * 删除对象
     *
     * @param object
     * @return
     */
    public void delete(Object object)
    {
        getSession().delete(object);
    }

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    public void delete(Class entityType, Serializable id)
    {
        delete(get(entityType, id));
    }

    /**
     * 通过主键获取单个对象
     *
     * @param entityType
     * @return
     */
    public <T> T get(Class<T> entityType, Serializable id)
    {
        return getSession().get(entityType, id);
    }

    /**
     * 删除对象集合
     *
     * @param entities
     * @return
     */
    public void deleteAll(Collection<?> entities)
    {
        for (Object entity : entities)
        {
            getSession().delete(entity);
        }
    }

    /**
     * 查询分页数据
     *
     * @param criteria
     */
    public List findByPage(Criteria criteria)
    {
        // 获得当前线程绑定的page对象
        Page page = PageMethod.getLocalPage();
        if (page == null)
        {
            return criteria.list();
        }

        // 查询分页总条数
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        page.setTotal(total);
        if (total == 0)
        {
            return Collections.emptyList();
        }
        // 查询分页数据
        criteria.setFirstResult((int) page.getStartRow());
        criteria.setMaxResults(page.getPageSize());
        criteria.setProjection(null);

        //分页默认只查单表
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        return criteria.list();
    }
}

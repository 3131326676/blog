package com.qumingbo.blog.service;

import com.qumingbo.blog.pojo.Type;

import java.util.List;

/**
 * <p>
 * 帖子类型表服务层接口
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
public interface TypeService {

    /**
     * 添加
     * @param type
     */
    void save(Type type);

    /**
     * 查询所有
     * @return
     */
    List<Type> getAll();

    /**
     * 前台查询所有
     * @return
     */
    List<Type> getTypeList();

    /**
     * 更新
     * @param type
     */
    void update(Type type);

    /**
     * 根据id启用
     * @param id
     */
    void enableById(Integer id);

    /**
     * 根据id弃用
     * @param id
     */
    void disableById(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Type getById(Integer id);
}

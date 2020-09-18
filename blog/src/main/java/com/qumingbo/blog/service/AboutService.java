package com.qumingbo.blog.service;

import com.qumingbo.blog.pojo.About;
import com.qumingbo.blog.utils.Page;

/**
 * <p>
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
public interface AboutService {

    /**
     * 保存
     * @param about
     */
    void save(About about);

    /**
     * 更新
     * @param about
     */
    void update(About about);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    About getById(Integer id);

    /**
     * 阅读
     * @return
     */
    About read();

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

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
     * 分页查询
     * @param page
     * @return
     */
    Page<About> getByPage(Page<About> page);
}

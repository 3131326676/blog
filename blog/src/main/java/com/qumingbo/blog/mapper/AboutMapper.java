package com.qumingbo.blog.mapper;

import com.qumingbo.blog.pojo.About;
import com.qumingbo.blog.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 管理员表Mapper
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Component
public interface AboutMapper {

    /**
     * 保存
     * @param about
     */
    void save(About about);

    /**
     * 根据id更新
     * @param about
     */
    void updateById(About about);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    About getById(Integer id);

    /**
     * 查询关于
     * @return
     */
    About getAbout();

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新启用状态
     * @param about
     */
    void updateEnable(About about);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<About> getByPage(Page<About> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    int getCountByPage(Page<About> page);
}

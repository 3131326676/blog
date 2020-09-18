package com.qumingbo.blog.mapper;

import com.qumingbo.blog.pojo.Blog;
import com.qumingbo.blog.utils.Page;
import com.qumingbo.blog.vo.BlogVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 博客表Mapper
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Component
public interface BlogMapper {

    /**
     * 保存
     * @param blog
     */
    void save(Blog blog);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Blog getById(String id);

    /**
     * 更新
     * @param blog
     */
    void update(Blog blog);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<BlogVo> getByPage(Page<BlogVo> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    int getCountByPage(Page<BlogVo> page);

    /**
     * 查询推荐阅读
     * @return
     */
    List<BlogVo> recomRead();

    /**
     * 查询时间轴
     * @return
     */
    List<BlogVo> getTimeLine();

    /**
     * 更新点赞量
     * @param blogId
     */
    void updateGoods(String blogId);

    /**
     * 根据博客id查询博客分类
     * @param id 博客id
     * @return 博客分类
     */
    Integer getBlogType(String id);
}

package com.qumingbo.blog.mapper;

import com.qumingbo.blog.pojo.Log;
import com.qumingbo.blog.utils.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 接口访问日志表Mapper
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Component
public interface LogMapper {

    /**
     * 保存
     * @param logger
     */
    void save(Log logger);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<Log> getByPage(Page<Log> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    int getCountByPage(Page<Log> page);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id列表删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询全部
     * @return
     */
    List<Log> getAll();
}

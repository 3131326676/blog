package com.qumingbo.blog.service;

import com.qumingbo.blog.pojo.Log;
import com.qumingbo.blog.utils.Page;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * <p>
 * 接口访问日志表服务层接口
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
public interface LogService {

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
    Page<Log> getByPage(Page<Log> page);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id集合删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询数据，构建成workbook用于导出
     * @return
     */
    Workbook export();
}

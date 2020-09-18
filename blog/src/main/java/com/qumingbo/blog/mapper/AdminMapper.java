package com.qumingbo.blog.mapper;

import com.qumingbo.blog.pojo.Admin;
import org.springframework.stereotype.Component;

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
public interface AdminMapper {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    Admin getByUsername(String username);

    /**
     * 查询管理员
     * @return
     */
    Admin getAdmin();

    /**
     * 更新
     * @param admin
     */
    void update(Admin admin);
}

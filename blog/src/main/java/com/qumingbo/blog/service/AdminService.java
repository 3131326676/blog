package com.qumingbo.blog.service;

import com.qumingbo.blog.pojo.Admin;

/**
 * <p>
 * 管理员表服务层接口
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
public interface AdminService {

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
     * 更新个人信息
     * @param admin
     */
    void updateInfo(Admin admin);

    /**
     * 更新密码
     * @param admin
     */
    void updatePassword(Admin admin);
}

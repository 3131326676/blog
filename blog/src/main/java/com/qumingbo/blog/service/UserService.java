package com.qumingbo.blog.service;

import com.qumingbo.blog.pojo.User;
import com.qumingbo.blog.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表服务层接口
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
public interface UserService {

    /**
     * 保存
     *
     * @param user
     */
    void save(User user);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<User> getByPage(Page<User> page);

    /**
     * 重置密码为123456
     * @param userIds
     */
    void resetPwd(List<Integer> userIds);

    /**
     * 注册
     * @param user
     */
    void register(User user);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 修改个人信息
     * @param user
     */
    void updateInfo(User user);

    /**
     * 查询评论数和收藏数
     * @return
     */
    Map<String, Object> getCommentAndCollectionCount();
}

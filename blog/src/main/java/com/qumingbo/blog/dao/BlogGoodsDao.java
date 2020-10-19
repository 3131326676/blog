package com.qumingbo.blog.dao;

import com.qumingbo.blog.pojo.BlogGoods;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: qumingbo
 * @Date: 2020/2/16 10:23
 * @Version 1.0
 */
@Repository
public interface BlogGoodsDao extends MongoRepository<BlogGoods, String> {

    /**
     * 根据用户id和博客id查询
     * @param userId
     * @param blogId
     * @return
     */
    int countByUserIdAndBlogId(Integer userId, String blogId);

}

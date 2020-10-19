package com.qumingbo.blog.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 评论点赞
 * @Author: qumingbo
 * @Date: 2020/2/16 10:22
 * @Version 1.0
 */
@Data
public class CommentGoods {

    @Id
    private String id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 评论id
     */
    private String commentId;

}

package com.qumingbo.blog;

import com.alibaba.fastjson.JSONObject;
import com.qumingbo.blog.dao.CommentDao;
import com.qumingbo.blog.pojo.Blog;
import com.qumingbo.blog.pojo.Comment;
import com.qumingbo.blog.pojo.User;
import com.qumingbo.blog.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    @Test
    public void testSave() {
        Comment comment = new Comment();
        comment.setId(idWorker.nextId() + "");
        comment.setCommentBlog("123");
        comment.setCommentContent("我是评论1");
        comment.setCommentUser(123);
        comment.setCommentGood(0);
        comment.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 插入帖子
        Blog blog = new Blog();
        blog.setBlogId("123123");
        blog.setBlogTitle("我是帖子");
        comment.setBlog(blog);
        // 插入用户
        User user = new User();
        user.setUserId(123);
        user.setName("鸡哥");
        comment.setUser(user);
        commentDao.save(comment);
    }

    @Test
    public void testFind() {
        List<Comment> list = commentDao.findAll();
        for (Comment comment : list) {
            System.out.println(comment);
        }
    }

    @Test
    public void testJpa() {
        List<Comment> commentList = commentDao.findByCommentUserEqualsAndCommentGoodGreaterThanEqual(123, 7);
        for (Comment comment1 : commentList) {
            System.out.println(comment1);
        }
    }

    @Test
    public void testFindById() {
        Comment comment = commentDao.findById("1228688036364046336").get();
        System.out.println(JSONObject.toJSONString(comment));
    }

    @Test
    public void testPage() {
        Comment comment = new Comment();
        Example<Comment> example = Example.of(comment);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Comment> page = commentDao.findAll(example, pageable);
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Comment> commentList = page.getContent();
        System.out.println(totalElements);
        System.out.println(totalPages);
        for (Comment comment1 : commentList) {
            comment1.setBlog(null);
            System.out.println(comment1);
        }
    }

    @Test
    public void testPage2() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Comment> page = commentDao.findAllByCommentContentIsLike(null, pageable);
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Comment> commentList = page.getContent();
        System.out.println(totalElements);
        System.out.println(totalPages);
        for (Comment comment1 : commentList) {
            comment1.setBlog(null);
            System.out.println(comment1);
        }
    }

    @Test
    public void test() {
        List<Comment> comments = commentDao.getByBlog("1226860815664066560");
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Test
    public void test2() {
        List<Comment> comments = commentDao.getByCommentGoods(0);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Test
    public void test3() {
        List<Comment> comments = commentDao.getByBlogTitle("设计模式");
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Test
    public void test4() {
        Comment comment = new Comment();
        Blog blog = new Blog();
        blog.setBlogTitle("设计模式");
        comment.setBlog(blog);
        List<Comment> comments = commentDao.getByBlog(comment);
        for (Comment c : comments) {
            System.out.println(c);
        }
    }

}

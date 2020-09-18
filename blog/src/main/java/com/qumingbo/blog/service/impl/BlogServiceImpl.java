package com.qumingbo.blog.service.impl;

import com.qumingbo.blog.dao.BlogGoodsDao;
import com.qumingbo.blog.dao.CollectionDao;
import com.qumingbo.blog.mapper.BlogMapper;
import com.qumingbo.blog.mapper.TypeMapper;
import com.qumingbo.blog.pojo.Blog;
import com.qumingbo.blog.pojo.BlogCollection;
import com.qumingbo.blog.pojo.BlogGoods;
import com.qumingbo.blog.pojo.Type;
import com.qumingbo.blog.pojo.User;
import com.qumingbo.blog.service.BlogService;
import com.qumingbo.blog.utils.IdWorker;
import com.qumingbo.blog.utils.Page;
import com.qumingbo.blog.utils.ShiroUtils;
import com.qumingbo.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 博客表服务实现类
 * </p>
 *
 * @author qumingbo
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Service
@Slf4j
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogGoodsDao blogGoodsDao;
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private IdWorker idWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Blog blog) {
        blog.setBlogId(idWorker.nextId() + "");
        blogMapper.save(blog);
        // 取出分类，当前分类博客数+1
        Integer blogType = blog.getBlogType();
        Type type = typeMapper.getById(blogType);
        type.setTypeBlogCount(type.getTypeBlogCount() + 1);
        typeMapper.update(type);
    }

    @Override
    public Blog getById(String id) {
        return blogMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Blog blog) {
        // 修改之前先进行查询
        Blog oldBlog = blogMapper.getById(blog.getBlogId());
        blogMapper.update(blog);
        // 判断分类有没有被修改，如果被修改了，旧的分类博客数-1，新的分类博客数+1
        Integer oldTypeId = oldBlog.getBlogType();
        Integer nowTypeId = blog.getBlogType();
        if (!oldTypeId.equals(nowTypeId)) {
            Type oldType = typeMapper.getById(oldTypeId);
            oldType.setTypeBlogCount(oldType.getTypeBlogCount() - 1);
            typeMapper.update(oldType);

            Type nowType = typeMapper.getById(nowTypeId);
            nowType.setTypeBlogCount(nowType.getTypeBlogCount() + 1);
            typeMapper.update(nowType);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogVo readById(String id) {
        Blog blog = blogMapper.getById(id);
        // 阅读，需要更新阅读数
        blog.setBlogRead(blog.getBlogRead() + 1);
        blogMapper.update(blog);
        // 将blog转为blogVo
        BlogVo blogVo = new BlogVo();
        BeanUtils.copyProperties(blog, blogVo);
        // 查询分类
        Type type = typeMapper.getById(blog.getBlogType());
        blogVo.setTypeName(type.getTypeName());
        return blogVo;
    }

    @Override
    public void deleteById(String id) {
        blogMapper.deleteById(id);
        // 取出分类，当前分类博客数-1
        Integer blogType = blogMapper.getBlogType(id);
        Type type = typeMapper.getById(blogType);
        type.setTypeBlogCount(type.getTypeBlogCount() - 1);
        typeMapper.update(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<BlogVo> getByPage(Page<BlogVo> page) {
        // 查询数据
        List<BlogVo> blogVoList = blogMapper.getByPage(page);
        page.setList(blogVoList);
        // 查询总数
        int totalCount = blogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public List<BlogVo> recomRead() {
        return blogMapper.recomRead();
    }

    @Override
    public List<BlogVo> getTimeLine() {
        return blogMapper.getTimeLine();
    }

    @Override
    public void goodByBlogAndUser(BlogGoods blogGoods) {
        User user = (User) ShiroUtils.getLoginUser();
        blogGoods.setUserId(user.getUserId());
        // 取出博客id，点赞数+1
        String blogId = blogGoods.getBlogId();
        blogMapper.updateGoods(blogId);
        try {
            blogGoods.setId(idWorker.nextId() + "");
            blogGoodsDao.save(blogGoods);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getGoodsCount(String blogId) {
        User user = (User) ShiroUtils.getLoginUser();
        int count = blogGoodsDao.countByUserIdAndBlogId(user.getUserId(), blogId);
        return count;
    }

    @Override
    public void collectionByBlogId(BlogCollection blogCollection) {
        User user = (User) ShiroUtils.getLoginUser();
        blogCollection.setUserId(user.getUserId());
        blogCollection.setUser(user);
        // 查询博客
        Blog blog = blogMapper.getById(blogCollection.getBlogId());
        blog.setBlogContent(null);
        blogCollection.setBlog(blog);

        blog.setBlogCollection(blog.getBlogCollection() + 1);
        blogMapper.update(blog);
        try {
            blogCollection.setCollectionId(idWorker.nextId() + "");
            collectionDao.save(blogCollection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCollectionCount(String blogId) {
        User user = (User) ShiroUtils.getLoginUser();
        int count = collectionDao.countByBlogIdAndUserId(blogId, user.getUserId());
        return count;
    }

    @Override
    public Page<BlogCollection> getCollectionByPage(Page<BlogCollection> page) {
        User user = (User) ShiroUtils.getLoginUser();
        BlogCollection blogCollection = new BlogCollection();
        blogCollection.setUserId(user.getUserId());
        Example example = Example.of(blogCollection);
        Pageable pageable = PageRequest.of(page.getCurrentPage() - 1, page.getPageSize());
        org.springframework.data.domain.Page p = collectionDao.findAll(example, pageable);
        page.setTotalCount((int) p.getTotalElements());
        page.setTotalPage(p.getTotalPages());
        page.setList(p.getContent());
        return page;
    }
}

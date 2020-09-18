package com.qumingbo.blog.controller;

import com.qumingbo.blog.pojo.Link;
import com.qumingbo.blog.service.LinkService;
import com.qumingbo.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 杨德石
 * @Date: 2020/2/9 17:06
 * @Version 1.0
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 添加
     * @param link
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Link link) {
        linkService.save(link);
        return new Result<>("添加成功！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Link link) {
        linkService.update(link);
        return new Result<>("修改成功！");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Link> get(@PathVariable Integer id) {
        Link link = linkService.getById(id);
        return new Result<>(link);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Link>> list() {
        List<Link> linkList = linkService.getAll();
        return new Result<>(linkList);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<Object> delete(@PathVariable Integer id) {
        linkService.deleteById(id);
        return new Result<>("删除成功！");
    }

}

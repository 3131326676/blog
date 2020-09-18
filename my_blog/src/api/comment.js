import request from '@/utils/request'
var group_name = 'comment'
export default {
  save(comment) { // 添加
    return request({
      url: `/${group_name}/save`,
      method: 'post',
      data: comment
    })
  },
  getByPage(page) { // 前台分页查询
    return request({
      url: `/${group_name}/getList`,
      method: 'post',
      data: page
    })
  },
  getList(blogId) { // 根据博客id查询
    return request({
      url: `/${group_name}/getByBlog/${blogId}`,
      method: 'get'
    })
  },
  goodById(commentGoods) { // 点赞
    return request({
      url: `/${group_name}/good`,
      method: 'post',
      data: commentGoods
    })
  },
  deleteById(id) {
    return request({
      url: `/${group_name}/deleteById/${id}`,
      method: 'delete'
    })
  }
}

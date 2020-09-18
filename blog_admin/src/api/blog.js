import request from '@/utils/request'
var group_name = 'blog'
export default {
  save(blog) { // 添加
    return request({
      url: `/${group_name}/save`,
      method: 'post',
      data: blog
    })
  },
  getByPage(page) { // 前台查询
    return request({
      url: `/${group_name}/getByPage`,
      method: 'post',
      data: page
    })
  },
  update(blog) { // 更新
    return request({
      url: `/${group_name}/update`,
      method: 'put',
      data: blog
    })
  },
  deleteById(id) { // 删除
    return request({
      url: `/${group_name}/delete/${id}`,
      method: 'delete'
    })
  },
  get(id) { // 根据id查询
    return request({
      url: `/${group_name}/get/${id}`,
      method: 'get'
    })
  }
}

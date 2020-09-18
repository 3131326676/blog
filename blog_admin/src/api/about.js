import request from '@/utils/request'
var group_name = 'about'
export default {
  save(about) { // 添加
    return request({
      url: `/${group_name}/save`,
      method: 'post',
      data: about
    })
  },
  getByPage(page) { // 前台查询
    return request({
      url: `/${group_name}/getByPage`,
      method: 'post',
      data: page
    })
  },
  update(about) { // 更新
    return request({
      url: `/${group_name}/update`,
      method: 'put',
      data: about
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
  },
  enableById(id) { // 根据id启用
    return request({
      url: `/${group_name}/enable/${id}`,
      method: 'put'
    })
  },
  disableById(id) { // 根据id查询
    return request({
      url: `/${group_name}/disable/${id}`,
      method: 'put'
    })
  }
}

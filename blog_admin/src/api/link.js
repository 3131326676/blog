import request from '@/utils/request'
var group_name = 'link'
export default {
  save(link) { // 添加
    return request({
      url: `/${group_name}/save`,
      method: 'post',
      data: link
    })
  },
  getList() { // 查询
    return request({
      url: `/${group_name}/list`,
      method: 'get'
    })
  },
  update(link) { // 更新
    return request({
      url: `/${group_name}/update`,
      method: 'put',
      data: link
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

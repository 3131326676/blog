import request from '@/utils/request'
var group_name = 'user'
export default {
  login(user) { // 登录
    return request({
      url: `/${group_name}/login`,
      method: 'post',
      data: user
    })
  },
  register(user) { // 注册
    return request({
      url: `/${group_name}/register`,
      method: 'post',
      data: user
    })
  },
  update(user) { // 更新
    return request({
      url: `/${group_name}/updateInfo`,
      method: 'put',
      data: user
    })
  },
  resetPwd(userIds) { // 重置密码
    return request({
      url: `/${group_name}/resetPwd`,
      method: 'put',
      data: userIds
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
  commentAndCollectionCount() { // 查询收藏数和评论数
    return request({
      url: `/${group_name}/commentAndCollectionCount`,
      method: 'get'
    })
  }
}

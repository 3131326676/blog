import request from '@/utils/request'
var group_name = 'admin'
export default {
  getAdmin() { // 查询管理员
    return request({
      url: `/${group_name}/getAdmin`,
      method: 'get'
    })
  },
  update(admin) { // 更新
    return request({
      url: `/${group_name}/updateInfo`,
      method: 'put',
      data: admin
    })
  },
  updatePassword(admin) { // 更新密码
    return request({
      url: `/${group_name}/updatePassword`,
      method: 'put',
      data: admin
    })
  }
}

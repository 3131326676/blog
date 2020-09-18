import request from '@/utils/request'
var group_name = 'comment'
export default {
  getByPage(page) { // 分页查询
    return request({
      url: `/${group_name}/getByPage`,
      method: 'post',
      data: page
    })
  },
  deleteById(id) {
    return request({
      url: `/${group_name}/deleteById/${id}`,
      method: 'delete'
    })
  }
}

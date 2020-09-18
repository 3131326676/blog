import request from '@/utils/request'
var group_name = 'about'
export default {
  read() { // 阅读
    return request({
      url: `/${group_name}/read`,
      method: 'get'
    })
  }
}

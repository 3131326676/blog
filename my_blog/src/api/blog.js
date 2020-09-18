import request from '@/utils/request'
var group_name = 'blog'
export default {
  getByPage(page) { // 前台查询
    return request({
      url: `/${group_name}/getByPage`,
      method: 'post',
      data: page
    })
  },
  getCollectionList(page) { // 分页查询我的收藏
    return request({
      url: `/${group_name}/getCollectionList`,
      method: 'post',
      data: page
    })
  },
  goodByBlog(blogGoods) { // 点赞
    return request({
      url: `/${group_name}/good`,
      method: 'post',
      data: blogGoods
    })
  },
  getGood(blogId) { // 查询点赞情况
    return request({
      url: `/${group_name}/getGood/${blogId}`,
      method: 'get'
    })
  },
  collectionByBlog(blogCollection) { // 收藏
    return request({
      url: `/${group_name}/collection`,
      method: 'post',
      data: blogCollection
    })
  },
  getCollection(blogId) { // 查询收藏情况
    return request({
      url: `/${group_name}/getCollection/${blogId}`,
      method: 'get'
    })
  },
  getTimeLine() { // 查询时间轴
    return request({
      url: `/${group_name}/getTimeLine`,
      method: 'get'
    })
  },
  recomRead() { // 推荐阅读
    return request({
      url: `/${group_name}/recomRead`,
      method: 'get'
    })
  },
  readById(id) { // 根据id阅读
    return request({
      url: `/${group_name}/read/${id}`,
      method: 'get'
    })
  }
}

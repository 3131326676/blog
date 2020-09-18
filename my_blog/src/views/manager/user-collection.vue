<template>
  <div>
    <div v-for="item in page.list" :key="item.collectionId" class="collection-container">
      <router-link :to="'/info/'+item.blog.blogId" class="collection-card">
        <div class="blog-title">{{ item.blog.blogTitle }}</div>
        <div class="blog-bottom">
          <div class="blog-time">{{ item.blog.createdTime }}</div>
          <div class="blog-meta">
            <a-icon type="eye" /> {{ item.blog.blogRead }}
            <a-icon type="heart" /> {{ item.blog.blogCollection }}
            <a-icon type="like" /> {{ item.blog.blogGoods }}
            <a-icon type="message" /> {{ item.blog.blogComment }}
          </div>
        </div>
      </router-link>
      <a-divider />
    </div>
    <div class="blog-pagination">
      <a-pagination :show-total="total => `共 ${total} 条`" show-quick-jumper :default-current="1" :total="page.totalCount" @change="pageChange" />
    </div>
  </div>
</template>
<script>
import blogApi from '@/api/blog'
export default {
  data() {
    return {
      page: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0,
        totalPage: 0,
        list: []
      }
    }
  },
  created() {
    this.getCollectionList()
  },
  methods: {
    pageChange(pageNumber) {
      this.page.currentPage = pageNumber
      this.getCollectionList()
    },
    getCollectionList() {
      blogApi.getCollectionList(this.page).then(res => {
        this.page = res.data
      })
    }
  }
}
</script>
<style scoped>
a {
  display: flex;
  flex-direction: column;
  color: #3e4149;
  width: 560px;
}

a:hover {
  color: #3e4149;
}

.blog-title {
  font-size: 16px;
  line-height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.blog-type {
  line-height: 40px;
}

.blog-bottom {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-size: 12px;
  color: #b8b8b8;
}

</style>

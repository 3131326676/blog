<template>
  <div>
    <div class="blog-bg">
      <div class="blog-title">{{ blog.blogTitle }}</div>
      <div class="blog-item">
        <div class="blog-time">发布时间：{{ blog.createdTime }}</div>
        <div class="blog-meta">
          <a-icon type="eye" /> {{ blog.blogRead }}阅读
          <a-icon type="heart" /> {{ blog.blogCollection }}收藏
          <a-icon type="like" /> {{ blog.blogGoods }}点赞
        </div>
      </div>
      <div v-highlight class="blog-content" v-html="blog.blogContent" />
      <div v-if="user.username" class="blog-action">
        <a href="javascript:void(0);" :class="isGood ? 'blog-good meta-active' : 'blog-good'" @click="saveGoods">
          <a-icon type="like" /> 点赞
        </a>
        <a href="javascript:void(0);" :class="isCollection ? 'blog-collection meta-active' : 'blog-collection'" @click="saveCollection">
          <a-icon type="heart" /> 收藏
        </a>
      </div>
    </div>

    <div v-if="user.username" class="user-comment">
      <a-textarea v-model="content" placeholder="请输入内容，不超过300字" :rows="4" />
      <div class="comment-button">
        <a-button type="primary" @click="saveComment">发表评论</a-button>
        <div v-show="countShow" class="content-count">
          还能输入 {{ commentContentCount }} 个字符
        </div>
      </div>
    </div>

    <div v-for="item in commentList" :key="item.id" class="comment-bg">
      <div class="comment-header">
        <img :src="item.user.header" class="header-img">
      </div>
      <div class="comment-container">
        <div class="comment-nickname">{{ item.user.nickname }}</div>
        <div class="comment-time">发表于 {{ item.createdTime }}</div>
        <div class="comment-content">{{ item.commentContent }}</div>
      </div>
      <a v-if="user.username" href="javascript:void(0);" :class="item.commentFlag ? 'comment-good meta-active': 'comment-good'" @click="saveCommentGoods(item)"><a-icon type="like" /> {{ item.commentGood }}</a>
    </div>
  </div>
</template>

<script>
import blogApi from '@/api/blog'
import commentApi from '@/api/comment'
import marked from 'marked'
export default {
  data() {
    return {
      content: '',
      blog: {},
      isGood: false, // 判断是否已经点赞
      isCollection: false, // 判断是否已经收藏
      user: this.$store.getters.getUser,
      commentList: [], // 评论列表
      countShow: false, // 控制是否显示字符个数提示
      commentContentCount: 300 // 显示还能输入的字符数量
    }
  },
  watch: {
    'content': function(newVal, oldVal) {
      if (this.content.length > 300) {
        this.content = this.content.substring(0, 300)
      }
      if (this.content.length > 0) {
        this.countShow = true
      } else {
        this.countShow = false
      }
      const newValLength = newVal ? newVal.length : 0
      const oldValLength = oldVal ? oldVal.length : 0
      this.commentContentCount = this.commentContentCount - (newValLength - oldValLength)
    },
    '$route.params.id': function(newVal, oldVal) {
      this.getBlogInfo(newVal)
      this.getComment()
      this.getGoods()
      this.getCollection()
    }
  },
  created() {
    // 在Vue实例创建完毕执行
    const blogId = this.$route.params.id
    this.getBlogInfo(blogId)
    this.getComment()
    this.getGoods()
    this.getCollection()
  },
  methods: {
    getBlogInfo(blogId) {
      blogApi.readById(blogId).then(res => {
        this.blog = res.data
        this.blog.blogContent = marked(this.blog.blogContent, { sanitize: true })
      })
    },
    getComment() {
      const blogId = this.$route.params.id
      commentApi.getList(blogId).then(res => {
        this.commentList = res.data
      })
    },
    saveGoods() {
      // 点赞
      if (!this.isGood) {
        const blogId = this.$route.params.id
        const obj = {
          blogId: blogId
        }
        blogApi.goodByBlog(obj).then(res => {
          this.$message.info(res.msg)
          this.getGoods()
        })
      } else {
        this.$message.error('您已点赞，请勿重复点赞')
      }
    },
    saveCommentGoods(comment) {
      // 点赞
      if (!comment.commentFlag) {
        const commentId = comment.id
        const obj = {
          commentId: commentId
        }
        commentApi.goodById(obj).then(res => {
          this.$message.info(res.msg)
          comment.commentGood = comment.commentGood + 1
          this.getComment()
        })
      } else {
        this.$message.error('您已点赞，请勿重复点赞')
      }
    },
    getGoods() {
      // 查询点赞
      const blogId = this.$route.params.id
      blogApi.getGood(blogId).then(res => {
        const flag = res.data
        if (flag === 0) {
          this.isGood = false
        } else {
          this.isGood = true
        }
      })
    },
    saveCollection() {
      // 收藏
      if (!this.isCollection) {
        const blogId = this.$route.params.id
        const obj = {
          blogId: blogId
        }
        blogApi.collectionByBlog(obj).then(res => {
          this.$message.info(res.msg)
          this.getCollection()
        })
      } else {
        this.$message.error('您已收藏，请勿重复收藏')
      }
    },
    getCollection() {
      // 查询收藏
      const blogId = this.$route.params.id
      blogApi.getCollection(blogId).then(res => {
        const flag = res.data
        if (flag === 0) {
          this.isCollection = false
        } else {
          this.isCollection = true
        }
      })
    },
    saveComment() {
      const blogId = this.$route.params.id
      const comment = {
        commentBlog: blogId,
        commentContent: this.content
      }
      commentApi.save(comment).then(res => {
        this.$message.info(res.msg)
        this.content = ''
        this.getComment()
      })
    }
  }
}
</script>

<style scoped>
.comment-button {
  margin: 15px;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
}

.content-count {
  margin-right: 15px;
}

.user-comment {
  margin-top: 15px;
  background-color: #fff;
  padding: 10px;
}
.comment-bg {
  background-color: #fff;
  margin-top: 15px;
  padding: 10px 25px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.comment-container {
  width: 580px;
  display: flex;
  flex-direction: column;
}

.comment-time {
  font-size: 12px;
  color: #9c9ea8;
}

.comment-nickname {
  font-weight: bold;
}

.header-img {
  width: 100px;
  height: 100px;
  border: 5px solid #e5e5e5;
  border-radius: 50%;
}

.blog-bg {
  background: #fff;
  padding: 40px 25px;
}

.blog-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  line-height: 70px;
}

.blog-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 30px;
  margin-top: 10px;
}

.blog-action {
  width: 300px;
  margin: auto;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  font-size: 24px;
}

.blog-good {
  color: #595959;
}

.blog-good:hover {
  color: #595959;
}

.comment-good {
  color: #595959;
}

.comment-good:hover {
  color: #595959;
}

.blog-collection {
  color: #595959;
}

.blog-collection:hover {
  color: #595959;
}

.meta-active {
  /* 标识当前是否已点赞，是否已收藏 */
  color: red;
}

.meta-active:hover {
  /* 标识当前是否已点赞，是否已收藏 */
  color: red !important;
}
</style>

<template>
  <div>
    <a-card title="友情链接" :head-style="headStyle">
      <div class="link-container">
        <a v-for="item in linkList" :key="item.linkId" target="_blank" :href="item.linkUrl">{{ item.linkName }}</a>
      </div>
    </a-card>
  </div>
</template>

<script>
import linkApi from '@/api/link'
export default {
  data() {
    return {
      headStyle: {
        fontSize: '18px',
        fontWeight: 'bold',
        lineHeight: '15px',
        borderLeft: '5px solid #409eff'
      },
      linkList: [] // 友情链接列表
    }
  },
  created() {
    this.getLinkList()
  },
  methods: {
    getLinkList() {
      linkApi.getList().then(res => {
        this.linkList = res.data
      })
    }
  }
}
</script>

<style scoped>
.link-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.link-container a {
  font-size: 16px;
  min-width: 60px;
  margin-right: 15px;
  color:black;
}

.link-container a:hover {
  color: aqua;
}
</style>

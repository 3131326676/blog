<template>
  <div>
    <a-card title="分类列表" :head-style="headStyle">
      <div class="type-content">
        <router-link v-for="item in typeList" :key="item.typeId" :style="randomRgb(item)" :to="'/blogList/'+item.typeId" class="type-item">{{ item.typeName }} （{{ item.typeBlogCount }}）</router-link>
      </div>
    </a-card>
  </div>
</template>
<script>
import typeApi from '@/api/type'
export default {
  data() {
    return {
      typeList: [], // 分类列表
      headStyle: {
        fontSize: '18px',
        fontWeight: 'bold',
        lineHeight: '15px',
        borderLeft: '5px solid #409eff'
      }
    }
  },
  created() {
    this.getTypeList()
  },
  methods: {
    getTypeList() {
      typeApi.getList().then(res => {
        this.typeList = res.data
        this.typeCount = this.typeList.length
      })
    },
    randomRgb(item) {
      const R = Math.floor(Math.random() * 255)
      const G = Math.floor(Math.random() * 255)
      const B = Math.floor(Math.random() * 255)
      return {
        color: 'rgb(' + R + ',' + G + ',' + B + ')'
      }
    }
  }
}
</script>
<style scope>
.type-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  align-items: flex-start;
}

.type-item {
  border-radius: 15px;
  margin-bottom: 5px;
  font-size: 16px;
}
</style>

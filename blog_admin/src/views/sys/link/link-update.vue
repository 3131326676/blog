<template>
  <div>
    <el-form ref="form" :model="link" label-width="80px" size="mini">
      <el-form-item label="链接名">
        <el-input v-model="link.linkName" />
      </el-form-item>
      <el-form-item label="链接地址">
        <el-input v-model="link.linkUrl" />
      </el-form-item>
      <el-form-item>
        <el-button link="primary" size="mini" @click="onSubmit">提交</el-button>
        <el-button size="mini">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import linkApi from '@/api/link'
export default {
  props: {
    link: {
      type: Object,
      default: null
    }
  },
  methods: {
    onSubmit() {
      linkApi.update(this.link).then(res => {
        this.$message.success(res.msg)
        this.$emit('closeUpdateDialog')
        this.$emit('getLinkList')
      })
    }
  }
}
</script>

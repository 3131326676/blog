<template>
  <div>
    <el-form ref="form" :model="type" label-width="80px" size="mini">
      <el-form-item label="分类名">
        <el-input v-model="type.typeName" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">提交</el-button>
        <el-button size="mini" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import typeApi from '@/api/type'
export default {
  data() {
    return {
      type: {}
    }
  },
  methods: {
    onSubmit() {
      typeApi.save(this.type).then(res => {
        this.$message.success(res.msg)
        this.$emit('closeAddDrawer')
        this.$emit('getTypeList')
      })
    },
    cancel() {
      this.$emit('closeAddDrawer')
    }
  }
}
</script>

<template>
  <div>
    <el-form ref="form" :model="adminUser" label-width="80px" size="mini">
      <el-form-item label="新密码">
        <el-input v-model="adminUser.password" type="password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import md5 from 'js-md5'
import adminApi from '@/api/admin'
export default {
  props: {
    adminUser: {
      type: Object,
      default: null
    }
  },
  methods: {
    onSubmit() {
      this.adminUser.password = md5(this.adminUser.password)
      adminApi.updatePassword(this.adminUser).then(res => {
        this.adminUser.password = null
        this.$message.success(res.msg)
        this.$emit('closeUpdateDialog')
      })
    }
  }

}
</script>

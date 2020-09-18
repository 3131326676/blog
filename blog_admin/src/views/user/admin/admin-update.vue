<template>
  <div>
    <el-form ref="form" :model="adminUser" label-width="80px" size="mini">
      <el-form-item label="账号">
        <el-input v-model="adminUser.username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-button type="primary" size="mini" @click="toUpdatePwd">点击修改</el-button>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="adminUser.name" />
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          class="avatar-uploader"
          :action="uploadUrl"
          :show-file-list="false"
          :on-success="uploadSuccess"
          :headers="headers"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item label="签名">
        <el-input v-model="adminUser.signature" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">修改</el-button>
      </el-form-item>
    </el-form>

    <!-- 修改弹窗 -->
    <el-dialog title="修改" :visible.sync="updateDialog">
      <admin-update-pwd :admin-user="adminUser" @closeUpdateDialog="closeUpdateDialog" />
    </el-dialog>

  </div>
</template>

<script>
import adminApi from '@/api/admin'
import AdminUpdatePwd from './admin-update-pwd'
import { getToken } from '@/utils/auth'
export default {
  components: {
    AdminUpdatePwd
  },
  data() {
    return {
      headers: { // 上传文件的请求头
        Authorization: getToken()
      },
      updateDialog: false, // 修改密码弹窗
      uploadUrl: process.env.VUE_APP_UPLOAD_URL, // 上传图片路径
      adminUser: {},
      imageUrl: '' // 上传图片回显
    }
  },
  watch: { // 监听器这里的function不能使用箭头函数替代
    'adminUser.header': function(newVal, oldVal) {
      this.imageUrl = newVal
    }
  },
  created() {
    this.getAdmin()
  },
  methods: {
    getAdmin() {
      adminApi.getAdmin().then(res => {
        this.adminUser = res.data
      })
    },
    onSubmit() {
      adminApi.update(this.adminUser).then(res => {
        this.$message.success(res.msg)
      })
    },
    toUpdatePwd() {
      this.updateDialog = true
    },
    closeUpdateDialog() {
      this.updateDialog = false
    },
    uploadSuccess(res, file) {
      this.$message.success(res.msg)
      this.imageUrl = res.data
      this.adminUser.header = res.data
    }
  }
}
</script>
<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

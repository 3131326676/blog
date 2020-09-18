<template>
  <div>
    <el-form ref="form" :model="blog" label-width="80px" size="mini">
      <el-form-item label="标题">
        <el-input v-model="blog.blogTitle" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="blog.blogType" clearable filterable placeholder="请选择" style="width: 100%">
          <el-option
            v-for="type in typeList"
            :key="type.typeId"
            :label="type.typeName"
            :value="type.typeId"
          />
        </el-select></el-form-item>
      <el-form-item label="封面">
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
      <el-form-item label="内容">
        <MarkdownPro v-model="blog.blogContent" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">提交</el-button>
        <el-button size="mini" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import blogApi from '@/api/blog'
import { MarkdownPro } from 'vue-meditor'
import { getToken } from '@/utils/auth'
export default {
  components: {
    MarkdownPro
  },
  props: {
    blog: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      imageUrl: this.blog.blogImage, // 上传图片回显
      headers: { // 上传文件的请求头
        Authorization: getToken()
      },
      uploadUrl: process.env.VUE_APP_UPLOAD_URL, // 上传图片路径
      typeList: this.$store.getters.typeList
    }
  },
  watch: { // 监听器这里的function不能使用箭头函数替代
    'blog.blogImage': function(newVal, oldVal) {
      this.imageUrl = newVal
    }
  },
  methods: {
    onSubmit() {
      blogApi.update(this.blog).then(res => {
        this.$message.success(res.msg)
        this.$emit('getByPage')
        this.$emit('closeUpdateDialog')
      })
    },
    cancel() {
      this.$emit('closeUpdateDialog')
    },
    uploadSuccess(res, file) {
      this.$message.success(res.msg)
      this.imageUrl = res.data
      this.blog.blogImage = res.data
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

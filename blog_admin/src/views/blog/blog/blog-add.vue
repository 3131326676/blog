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
        <mavon-editor v-model="artict" :ishljs="true" @change="change" />
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
import { getToken } from '@/utils/auth'
export default {
  data() {
    return {
      blog: {
        blogId: '',
        blogTitle: '',
        blogImage: null,
        blogContent: ''
      },
      imageUrl: null, // 上传图片回显
      headers: { // 上传文件的请求头
        Authorization: getToken()
      },
      artict: '',
      uploadUrl: process.env.VUE_APP_UPLOAD_URL, // 上传图片路径
      typeList: this.$store.getters.typeList
    }
  },
  methods: {
    change(value, render) {
      // render 为 markdown 解析后的结果(转化成了HTML格式）
      this.blog.blogContent = render
      console.log(render)
      console.log('赋值' + this.blog.blogContent)
    },
    onSubmit() {
      blogApi.save(this.blog).then(res => {
        console.log('提交' + this.blog.blogContent)
        this.$message.success(res.msg)
        this.$emit('getByPage')
        this.$emit('closeAddDialog')
      })
    },
    cancel() {
      this.$emit('closeAddDialog')
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

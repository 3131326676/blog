<template>
  <div>
    <el-form ref="form" :model="music" label-width="80px" size="mini">
      <el-form-item label="名称">
        <el-input v-model="music.name" />
      </el-form-item>
      <el-form-item label="歌手">
        <el-input v-model="music.artist" />
      </el-form-item>
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
      <el-form-item label="歌曲文件">
        <el-upload
          :action="uploadUrl"
          :headers="headers"
          :limit="1"
          :on-success="uploadMusicSuccess"
          :file-list="fileList"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="歌词">
        <el-input v-model="music.lrc" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">提交</el-button>
        <el-button size="mini">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import musicApi from '@/api/music'
import { getToken } from '@/utils/auth'
export default {
  props: {
    music: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      imageUrl: this.music.musicImage, // 上传图片回显
      headers: { // 上传文件的请求头
        Authorization: getToken()
      },
      uploadUrl: process.env.VUE_APP_UPLOAD_URL, // 上传图片路径
      fileList: [], // 上传文件的文件列表
      typeList: this.$store.getters.typeList
    }
  },
  watch: { // 监听器这里的function不能使用箭头函数替代
    'music.cover': function(newVal, oldVal) {
      this.imageUrl = newVal
    },
    'music.url': function(newVal, oldVal) {
      this.fileList = [
        { name: this.music.name, url: this.music.url }
      ]
    }
  },
  methods: {
    onSubmit() {
      musicApi.update(this.music).then(res => {
        this.$message.success(res.msg)
        this.$emit('getByPage')
        this.$emit('closeUpdateDialog')
      })
    },
    uploadMusicSuccess(res, file) {
      this.$message.success(res.msg)
      this.music.url = res.data
    },
    uploadSuccess(res, file) {
      this.$message.success(res.msg)
      this.imageUrl = res.data
      this.music.musicImage = res.data
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

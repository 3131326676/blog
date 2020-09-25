<template>
  <div>
    <el-form ref="form" :model="about" label-width="80px" size="mini">
      <el-form-item label="标题">
        <el-input v-model="about.aboutTitle" />
      </el-form-item>
      <el-form-item label="内容">
        <mavon-editor ref="md" v-model="about.aboutContent" :tab-size="4" @imgAdd="$imgAdd" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">提交</el-button>
        <el-button size="mini" @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import aboutApi from '@/api/about'
import axios from 'axios'
export default {
  data() {
    return {
      about: {}
    }
  },
  methods: {
    $imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      const formdata = new FormData()
      formdata.append('file', $file)
      axios({
        url: this.uploadUrl,
        method: 'POST',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data; charset=utf-8' }
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        // $vm.$img2Url 详情见本页末尾
        this.$refs.md.$img2Url(pos, res.data.data)
      })
    },
    onSubmit() {
      aboutApi.save(this.about).then(res => {
        this.$message.success(res.msg)
        this.$emit('getByPage')
        this.$emit('closeAddDialog')
      })
    },
    cancel() {
      this.$emit('closeAddDialog')
    }
  }
}
</script>

<template>
  <div>
    <el-form ref="form" :model="about" label-width="80px" size="mini">
      <el-form-item label="标题">
        <el-input v-model="about.aboutTitle" />
      </el-form-item>
      <el-form-item label="内容">
        <tinymce v-model="about.aboutContent" />
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
import Tinymce from '@/components/Tinymce/index'
export default {
  components: {
    Tinymce
  },
  props: {
    about: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
    }
  },
  methods: {
    onSubmit() {
      aboutApi.update(this.about).then(res => {
        this.$message.success(res.msg)
        this.$emit('getByPage')
        this.$emit('closeUpdateDialog')
      })
    },
    cancel() {
      this.$emit('closeUpdateDialog')
    }
  }
}
</script>

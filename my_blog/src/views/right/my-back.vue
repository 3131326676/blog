<template>
  <div>
    <a-card class="my-back-card" hoverable>
      <img src="@/assets/my-back.jpg" class="my-back">
      <img :src="adminInfo.header" class="my-header">
      <div class="my-bottom-container">
        <div class="my-nickname">{{ adminInfo.name }}</div>
        <div class="my-signature">{{ adminInfo.signature }}</div>
        <a-divider />
        <div class="social-container">
          <div class="social-text">社交：</div>
          <div class="social-item">
            <a href="https://gitee.com/qumingbo98">
              <img src="@/assets/icons/gitee.png" class="social-icon">
            </a>
            <a href="http://wpa.qq.com/msgrd?v=3&uin=3131326676&site=qq&menu=yes">
              <img src="@/assets/icons/qq.png" class="social-icon">
            </a>
            <a href="https://github.com/3131326676">
              <img src="@/assets/icons/github.png" class="social-icon">
            </a>
          </div>
        </div>
      </div>
    </a-card>
  </div>
</template>
<script>
import adminApi from '@/api/admin'
export default {
  data() {
    return {
      adminInfo: {} // 管理员信息
    }
  },
  created() {
    this.getAdminInfo()
  },
  methods: {
    getAdminInfo() {
      adminApi.getAdmin().then(res => {
        this.adminInfo = res.data
      })
    }
  }
}
</script>
<style>
.my-back {
  position: absolute;
  width: 100%;
  z-index: 1;
  top: 0;
}

.my-back-card .ant-card-body {
  padding: 0 !important;
}

.my-header {
  position: relative;
  display: block;
  width: 100px;
  height: 100px;
  border: 5px solid #e5e5e5;
  margin: 75px auto 10px;
  border-radius: 50%;
  z-index: 10;
}

.my-bottom-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 280px;
  margin: auto;
  margin-bottom: 30px;
}

.my-nickname {
  font-size: 22px;
  font-weight: bold;
  line-height: 60px;
}

.my-signature {
  font-size: 15px;
  color: #878d99;
}

.social-container {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.social-text {
  font-size: 15px;
  color: #878d99;
}

.social-icon {
  width: 40px;
  height: 40px;
}

.social-item {
  width: 200px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.join-item {
  width: 200px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

</style>

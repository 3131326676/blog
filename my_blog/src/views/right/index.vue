<template>
  <div>
    <div class="my-container">
      <my-back />
      <recommend />
      <my-link />
      <type-view />
    </div>
  </div>
</template>

<script>
import MyBack from './my-back'
import Recommend from './recommend'
import MyLink from './link'
import TypeView from './type-view'
export default {
  components: {
    MyBack,
    Recommend,
    MyLink,
    TypeView
  }
}
</script>

<style scoped>
.my-container {
  width: 350px;
  padding: 0 10px;
}

</style>

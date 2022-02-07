<template>
  <div class="auth">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: "AuthLayout"
}
</script>

<style scoped>
.auth{
  background: #f0f2f5;
  height: 100vh;
  width: 100vw;
  padding: 110px 0 144px;
}
</style>
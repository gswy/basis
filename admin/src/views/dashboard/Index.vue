<template>
  <PageHeader />
  <div class="right-table">
    asd
  </div>
</template>

<script>
import {ref} from 'vue'
import PageHeader from "@components/PageHeader";

export default {
  components: {
    PageHeader
  },
  setup() {
    const loading = ref(false)             // 页面加载状态

    return {
      loading,
    }
  },
};
</script>
<style>

</style>
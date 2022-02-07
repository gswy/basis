<template>
  <a-page-header
    :ghost="false"
    style="border: 1px solid rgb(235, 237, 240)"
    :title="route.name"
    :breadcrumb="{routes}"
    sub-title=""
  />
</template>
<script>
import {useRoute, useRouter} from "vue-router";

export default {
  setup() {
    const route = useRoute()
    const router = useRouter()
    const routes = [];
    router.currentRoute.value.matched.forEach((item) => {
      routes.push({
        path: item.path,
        breadcrumbName: item.name
      })
    });

    return {
      route,
      routes
    }
  },
};
</script>
<style>

</style>
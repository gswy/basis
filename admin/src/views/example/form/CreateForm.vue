<template>
  <a-modal v-model:visible="state.visible" title="创建管理员" :maskClosable="false">

    asdasd

    <template #footer>
      <a-button @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" :loading="state.loading" @click="handleOk">提交</a-button>
    </template>
  </a-modal>
</template>

<script>
import {reactive} from "vue";

export default {
  setup() {
    // 页面基础状态，loading等
    const state = reactive({
      visible: false,   // 开启状态
      loading: false,   // 加载状态
    })

    // 表单
    const form = reactive({

    })

    // 打开
    const add = () => {
      state.visible = true
    }

    // 提交
    const handleOk = () => {
      state.loading = true
    }

    // 关闭页面
    const handleCancel = () => {
      state.loading = false
      state.visible = false
    }
    return {
      state,
      form,

      add,
      handleOk,
      handleCancel,
    }
  }
}
</script>

<style>

</style>
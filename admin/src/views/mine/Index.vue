<template>
  <PageHeader />
  <div class="right-table">
    <a-spin tip="保存中..." :spinning="loading">
      <a-form :model="form" :label-col="{span: 6}" :wrapper-col="{span: 10}" style="margin-top: 20px">
        <a-form-item label="用户名">
          <a-input v-model:value="store.state.auth.username" disabled />
        </a-form-item>
        <a-form-item label="密码">
          <a-input-password v-model:value="form.password" placeholder="密码留空则不会更改" />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 10, offset: 6 }">
          <a-button type="primary" @click="handleSubmit">保存</a-button>
        </a-form-item>
      </a-form>
    </a-spin>
  </div>
</template>

<script>
import {reactive, ref, toRaw} from 'vue';
import PageHeader from "@components/PageHeader";
import {useStore} from "vuex";
import {updateMine} from '@api/mine';
import {message} from "ant-design-vue";

export default {
  components: {
    PageHeader
  },
  setup() {
    const loading = ref(false)             // 页面加载状态
    const store = useStore();
    const form = reactive({
      password: '',
    });

    // 提交数据
    const handleSubmit = () => {
      loading.value = true;
      updateMine(toRaw(form)).then((data) => {
        message.success(data.message)
        form.password = '';
      }).catch((data) => {
        message.error(data.message)
      }).finally(() => {
        loading.value = false;
      })
    }

    return {
      loading,
      form,
      store,

      handleSubmit,
    }
  },
};
</script>
<style>

</style>
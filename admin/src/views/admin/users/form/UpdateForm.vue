<template>
  <a-modal v-model:visible="state.visible" title="修改管理员" :maskClosable="false">
    <a-form :model="form" :label-col="{span: 4}" :wrapper-col="{span: 20}">
      <a-form-item
        label="用户名"
        :required="true"
        :validateStatus="errors.username !== null ? 'error' : ''"
        :help="errors.username"
        @change="() => {errors.username = null}"
      >
        <a-input disabled v-model:value="form.username" />
      </a-form-item>
      <a-form-item
        label="密码"
        :required="true"
        :validateStatus="errors.password !== null ? 'error' : ''"
        :help="errors.password"
        @change="() => {errors.password = null}"
      >
        <a-input-password v-model:value="form.password" placeholder="密码留空，则不修改密码。" />
      </a-form-item>
      <a-form-item
        label="状态"
        :required="true"
        :validateStatus="errors.status !== null ? 'error' : ''"
        :help="errors.status"
      >
        <a-switch checked-children="启用" un-checked-children="禁用" v-model:checked="form.status" />
      </a-form-item>
    </a-form>

    <template #footer>
      <a-button @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" :loading="state.loading" @click="handleOk">提交</a-button>
    </template>
  </a-modal>
</template>

<script>
import {reactive, toRaw} from "vue";
import {updateAdminUser, showAdminUser} from "@api/admin";
import {message} from "ant-design-vue";

export default {
  props: {
    ok: Function
  },
  setup(props) {
    // 页面基础状态，loading等
    const state = reactive({
      id: null,         // 要修改的id
      visible: false,   // 开启状态
      loading: false,   // 加载状态
    })

    // 表单
    const form = reactive({
      username: '',
      password: '',
      status: true,
    })

    // 错误信息
    const errors = reactive({
      username: null,
      password: null,
      status: null,
    })

    // 打开
    const edit = (id) => {
      state.id = id;
      state.visible = true;
      loadData();
    }

    // 加载数据
    const loadData = () => {
      state.loading = true;
      showAdminUser(state.id).then((data) => {
        form.username = data.username;
        form.status = data.status;
      }).catch(e => {
        message.warn(e.message)
      }).finally(() => {
        state.loading = false;
      })
    }

    // 提交
    const handleOk = () => {
      state.loading = true

      console.log(form, updateAdminUser);
      updateAdminUser(state.id, toRaw(form))
        .then((data) => {
          message.success(data.message)
          state.visible = false
          props.ok()
        })
        .catch(({status, data}) => {
          if (status === 422) {
            errors.username = data.errors.username || null;
            errors.password = data.errors.password || null;
            errors.status = data.errors.status || null;
          }
        })
        .finally(() => {
          state.loading = false;
        })
    }

    // 关闭页面
    const handleCancel = () => {
      state.loading = false
      state.visible = false
      state.id = null

      form.username = ''
      form.password = ''
      form.status = true
    }
    return {
      state,
      form,
      errors,

      edit,
      handleOk,
      handleCancel,
    }
  }
}
</script>

<style>

</style>
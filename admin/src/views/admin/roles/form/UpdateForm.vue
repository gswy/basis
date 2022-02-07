<template>
  <a-modal v-model:visible="state.visible" title="修改角色" :maskClosable="false">
    <a-form :model="form" :label-col="{span: 4}" :wrapper-col="{span: 20}">
      <a-form-item
        label="角色名"
        :required="true"
        :validateStatus="errors.name !== null ? 'error' : ''"
        :help="errors.name"
        @change="() => {errors.name = null}"
      >
        <a-input v-model:value="form.name" />
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
import {updateRole, showRole} from "@api/admin";
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
      name: '',
    })

    // 错误信息
    const errors = reactive({
      name: null,
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
      showRole(state.id).then((data) => {
        form.name = data.name;
      }).catch(e => {
        message.warn(e.message)
      }).finally(() => {
        state.loading = false;
      })
    }

    // 提交
    const handleOk = () => {
      state.loading = true

      updateRole(state.id, toRaw(form))
        .then((data) => {
          message.success(data.message)
          state.visible = false
          props.ok()
        })
        .catch(({status, data}) => {
          if (status === 422) {
            errors.name = data.errors.name || null;
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

      form.name = ''
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
<template>
  <a-modal v-model:visible="state.visible" title="分配角色" :maskClosable="false" :width="600">
    <a-form :model="form" :label-col="{span: 4}" :wrapper-col="{span: 20}">
      <a-form-item label="选择角色">
        <a-checkbox-group v-model:value="form">
          <template v-for="(role, key) in roles" :key="key">
            <a-checkbox :value="role.id">{{ role.name }}</a-checkbox>
          </template>
        </a-checkbox-group>
      </a-form-item>
    </a-form>

    <template #footer>
      <a-button @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" :loading="state.loading" @click="handleOk">提交</a-button>
    </template>
  </a-modal>
</template>

<script>
import {reactive, ref, toRaw} from "vue";
import {getAdminUserRole, updateAdminUserRole, getRoleList} from "@api/admin";
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

    // 角色列表
    const roles = ref([]);

    // 表单
    const form = ref([])

    // 打开
    const edit = (id) => {
      state.id = id;
      state.visible = true;
      loadRole();
      loadData();
    }

    // 加载角色列表
    const loadRole = () => {
      state.loading = true
      getRoleList().then((data) => {
        roles.value = data;
      }).catch(({data}) => {
        message.error(data.message)
      }).finally(() => {
        state.loading = false
      })
    }

    // 加载数据
    const loadData = () => {
      state.loading = true;
      getAdminUserRole(state.id).then((data) => {
        form.value = data
      }).catch(e => {
        message.warn(e.message)
      }).finally(() => {
        state.loading = false;
      })
    }

    // 提交
    const handleOk = () => {
      state.loading = true;
      updateAdminUserRole(state.id, toRaw(form.value))
        .then((data) => {
          message.success(data.message)
          state.visible = false
          props.ok()
        })
        .catch(({data}) => {
          message.error(data.message)
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

      form.value = [];
    }
    return {
      state,
      roles,
      form,

      edit,
      handleOk,
      handleCancel,
    }
  }
}
</script>

<style>

</style>
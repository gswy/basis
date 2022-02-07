<template>
  <a-modal v-model:visible="state.visible" title="分配权限" :maskClosable="false" :width="800">
    <a-form :model="form" :wrapper-col="{span: 24}">
      <a-form-item>
        <a-checkbox-group v-model:value="form">
          <template v-for="(parentPermission, parentKey) in permissions" :key="parentKey">
            <div style="margin-bottom: 15px;">
              <div>
                <a-checkbox :value="parentPermission.id" style="margin: 0 5px 5px 0">{{ parentPermission.name }}</a-checkbox>
              </div>
              <div style="margin-bottom: 5px">
                <template v-for="(childPermission, childKey) in parentPermission.children" :key="childKey">
                  <a-checkbox :value="childPermission.id" style="margin: 0 5px 5px 0">{{ childPermission.name }}</a-checkbox>
                </template>
              </div>
            </div>
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
import {getRolePermission, updateRolePermission, getPermissionList} from "@api/admin";
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

    // 权限列表
    const permissions = ref([]);

    // 表单
    const form = ref([])

    // 打开
    const edit = (id) => {
      state.id = id;
      state.visible = true;
      loadPermission();
      loadData();
    }

    // 加载角色列表
    const loadPermission = () => {
      state.loading = true
      getPermissionList().then((data) => {
        permissions.value = data;
      }).catch(({data}) => {
        message.error(data.message)
      }).finally(() => {
        state.loading = false
      })
    }

    // 加载数据
    const loadData = () => {
      state.loading = true;
      getRolePermission(state.id).then((data) => {
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
      updateRolePermission(state.id, toRaw(form.value))
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
      permissions,
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
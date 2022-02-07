<template>
  <PageHeader />
  <div class="right-table">
    <a-form layout="inline" :model="queryParams" style="margin-bottom: 20px;">
      <a-form-item>
        <a-button type="primary" @click="createForm.add()">创建</a-button>
      </a-form-item>
    </a-form>
    <s-table
      ref="table"
      :columns="column"
      :loadData="loadData"
      :queryParams="queryParams"
    >
      <template v-slot:action="{ text }">
        <!-- 修改按钮 -->
        <a-button type="primary" @click="updateForm.edit(text)" ghost size="small">
          <template #icon><EditOutlined/></template> 修改
        </a-button>&nbsp;
        <!-- 修改权限 -->
        <a-button type="primary" @click="permissionForm.edit(text)" ghost size="small">
          <template #icon><ApartmentOutlined/></template> 权限
        </a-button>&nbsp;
        <!-- 删除按钮 -->
        <a-popconfirm
          title="确定删除？"
          placement="bottomRight"
          cancel-text="取消"
          ok-text="确定"
          @confirm="deleteData(text)"
        >
          <a-button danger ghost size="small">
            <template #icon><DeleteOutlined/></template> 删除
          </a-button>
        </a-popconfirm>
      </template>
    </s-table>
    <CreateForm ref="createForm" :ok="() => {table.refresh()}" />
    <UpdateForm ref="updateForm" :ok="() => {table.refresh()}" />
    <PermissionForm ref="permissionForm" :ok="() => {table.refresh()}" />
  </div>
</template>

<script>
import STable from "@components/STable";
import PageHeader from "@components/PageHeader";
import {getRoles, deleteRole} from "@api/admin";
import {reactive, ref} from "vue";
import CreateForm from "./form/CreateForm";
import UpdateForm from "./form/UpdateForm";
import PermissionForm from "./form/PermissionForm";
import {EditOutlined,DeleteOutlined, ApartmentOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

export default {
  components: {
    STable,
    PageHeader,
    CreateForm,
    UpdateForm,
    PermissionForm,
    EditOutlined,
    DeleteOutlined,
    ApartmentOutlined,
  },
  setup() {
    // table ref
    const table = ref(null)
    const createForm = ref(null)
    const updateForm = ref(null)
    const permissionForm = ref(null)

    // 查询条件
    const queryParams = reactive({})

    // 查询数据
    const loadData = (params) => {
      return getRoles(params)
    }

    // 删除数据
    const deleteData = (id) => {
      deleteRole(id).then((data) => {
        message.success(data.message)
        table.value.refresh()
      }).catch(({data}) => {
        message.error(data.message)
      })
    }

    return {
      table,
      queryParams,
      createForm,
      updateForm,
      permissionForm,

      loadData,
      deleteData,

      column: [
        {
          title: 'ID',
          dataIndex: 'id',
          width: 100,
        },
        {
          title: '角色名',
          dataIndex: 'name',
        },
        {
          title: '创建时间',
          width: 200,
          dataIndex: 'created_at',
        },
        {
          title: '操作',
          dataIndex: 'id',
          width: 250,
          slots: {
            customRender: 'action',
          },
        },
      ]
    }
  },
};
</script>
<style>

</style>
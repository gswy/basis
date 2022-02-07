<template>
  <PageHeader />
  <div class="right-table">
    <a-form layout="inline" :model="queryParams" style="margin-bottom: 20px;">
      <a-form-item label="用户名">
        <a-input v-model:value="queryParams.username" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="table.refresh()">
          <template #icon><SearchOutlined /></template>
          搜索
        </a-button>
        <a-button @click="table.refresh(true)" style="margin-left: 10px">重置</a-button>
      </a-form-item>
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
      <template #status="{ text }">
        <a-tag v-if="text" color="green">正常</a-tag>
        <a-tag v-if="!text" color="orange">封禁</a-tag>
      </template>

      <template v-slot:action="{ text }">
        <!-- 修改按钮 -->
        <a-button type="primary" @click="updateForm.edit(text)" ghost size="small">
          <template #icon><EditOutlined/></template> 修改
        </a-button>&nbsp;
        <!-- 修改角色 -->
        <a-button type="primary" @click="roleForm.edit(text)" ghost size="small">
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
    <RoleForm ref="roleForm" :ok="() => {table.refresh()}" />
  </div>
</template>

<script>
import STable from "@components/STable";
import PageHeader from "@components/PageHeader";
import {getAdminUsers, deleteAdminUser} from "@api/admin";
import {reactive, ref} from "vue";
import CreateForm from "./form/CreateForm";
import UpdateForm from "./form/UpdateForm";
import RoleForm from "./form/RoleForm";
import {SearchOutlined, EditOutlined,DeleteOutlined,ApartmentOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

export default {
  components: {
    STable,
    PageHeader,
    CreateForm,
    UpdateForm,
    RoleForm,
    SearchOutlined,
    EditOutlined,
    DeleteOutlined,
    ApartmentOutlined,
  },
  setup() {
    // table ref
    const table = ref(null)
    const createForm = ref(null)
    const updateForm = ref(null)
    const roleForm = ref(null)

    // 查询条件
    const queryParams = reactive({})

    // 查询数据
    const loadData = (params) => {
      return getAdminUsers(params)
    }

    // 删除数据
    const deleteData = (id) => {
      deleteAdminUser(id).then((data) => {
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
      roleForm,

      loadData,
      deleteData,

      column: [
        {
          title: 'ID',
          dataIndex: 'id',
          width: 120,
        },
        {
          title: '用户名',
          dataIndex: 'username',
        },
        {
          title: '状态',
          dataIndex: 'status',
          width: 120,
          slots: {
            customRender: 'status',
          },
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
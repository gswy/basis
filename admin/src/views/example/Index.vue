<template>
  <PageHeader/>
  <div class="right-table">
    <a-form
      layout="inline"
      :model="queryParams"
      style="margin-bottom: 20px;"
    >
      <a-form-item label="name">
        <a-input v-model:value="queryParams.name" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="loadData(queryParams)">搜索</a-button>
        <a-button @click="() => {queryParams = {}; loadData(queryParams)}" style="margin-left: 10px">重置</a-button>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="createForm.add()">创建</a-button>
      </a-form-item>
    </a-form>
    <s-table
      ref="table"
      :row-key="record => record.id"
      :columns="column"
      :loadData="loadData"
    >
    </s-table>
    <CreateForm ref="createForm" />
    <UpdateForm ref="updateForm" />
  </div>
</template>

<script>
import STable from "@components/STable";
import PageHeader from "@components/PageHeader";
import {getExample} from "@api/example";
import {reactive, ref} from "vue";
import CreateForm from "./form/CreateForm";
import UpdateForm from "./form/UpdateForm";

export default {
  components: {
    STable,
    PageHeader,
    CreateForm,
    UpdateForm,
  },
  setup() {
    // table ref
    const table = ref(null)
    const createForm = ref(null)
    const updateForm = ref(null)

    // 查询条件
    const queryParams = reactive({})

    // 查询数据
    const loadData = (params) => {
      return getExample(params)
    }

    return {
      table,
      queryParams,
      loadData,
      createForm,
      updateForm,

      column: [
        {
          title: '名称',
          dataIndex: 'name',
        },
        {
          title: '操作',
          dataIndex: 'action',
        },
      ]
    }
  },
};
</script>
<style>

</style>
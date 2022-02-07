<template>
  <div>
    <a-table
      :row-key="record => record.id"
      :dataSource="data"
      :loading="loading"
      :pagination="pagination"
      @change="handleTableChange"
      :columns="columns"
    >
      <template v-for="(slotItem, slotKey) in slots" :key="slotKey" v-slot:[slotItem.name]="{text, record}">
        <slot :name="slotItem.name" v-bind:text="text" v-bind:record="record"></slot>
      </template>

<!--      <template #status="{text, record}">-->
<!--        <slot name="status" v-bind:text="text" v-bind:record="record">111</slot>-->
<!--      </template>-->
<!--      <template #action="{text, record}">-->
<!--        <slot name="action" v-bind:text="text" v-bind:record="record">111</slot>-->
<!--      </template>-->
    </a-table>
  </div>
</template>
<script>
import {reactive, ref, watch, useSlots} from "vue";

export default {
  props: {
    loadData: Function,
    queryParams: Object,
    columns: Array,
  },

  setup(props) {

    // 获取插槽
    const slots = [];
    const lsSlots = useSlots();
    for (let name in lsSlots) {
      slots.push({
        name: name,
      })
    }

    // 请求参数
    const params = ref({});

    // 监听数据变更
    watch(props.queryParams, value => {
      params.value = Object.assign(params.value, value)
    })

    // 初始化分页
    const pagination = reactive({
      total: 0,
      current: 0,
      pageSize: 0,
    })
    // 初始化表格数据
    const data = ref([]);

    // 表格加载状态
    const loading = ref(false);

    // 加载数据
    const loadData = () => {
      loading.value = true;
      props.loadData(params.value).then((result) => {
        pagination.pageSize = result.page_size;
        pagination.current = result.current;
        pagination.total = result.total;
        data.value = result.data
      }).finally(() => {
        loading.value = false;
      })
    }

    // 加载数据
    loadData()

    // 处理表格变化
    const handleTableChange = (page) => {
      params.value['page'] = page.current
      loadData()
    }

    // 刷新表格
    const refresh = (force = false) => {
      if (force) {
        params.value = {}
      }
      loadData()
    }

    return {
      slots,
      data,
      loading,
      pagination,

      handleTableChange,
      refresh,
    }
  },
};
</script>
<style>

</style>
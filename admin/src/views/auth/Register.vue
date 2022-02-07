<template>
  <div class="action-box">
    <a-card :bordered="false">
      <a-form :model="formState">
        <a-form-item>
          <a-input size="large" v-model:value="formState.account" placeholder="用户名">
            <template #prefix>
              <UserOutlined style="margin-right: 10px" />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input size="large" v-model:value="formState.password" placeholder="密码">
            <template #prefix>
              <LockOutlined style="margin-right: 10px" />
            </template>
          </a-input>
        </a-form-item>

        <a-button type="primary" size="large" block @click="onSubmit">登录</a-button>
      </a-form>

      <div class="auth-footer">
        <a-row type="flex" justify="space-between">
          <a-col :span="4" style="text-align: left">
            <router-link class="auth-link" to="/auth/login">立即登录</router-link>
          </a-col>
          <a-col :span="4" style="text-align: right">
            <router-link class="auth-link" to="/auth/forget">忘记密码</router-link>
          </a-col>
        </a-row>
      </div>

    </a-card>
  </div>
</template>

<script>
import {defineComponent, reactive, toRaw} from 'vue'
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue'

export default defineComponent({
  components: {
    UserOutlined,
    LockOutlined,
  },
  setup() {
    const formState = reactive({
      account: '',
      password: '',
    });

    const onSubmit = () => {
      console.log('submit!', toRaw(formState));
    };

    return {
      formState,
      onSubmit,
    };
  }
})
</script>

<style scoped>

</style>
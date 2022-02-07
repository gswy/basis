<template>
  <div class="action-box">
    <a-card :bordered="false">
      <a-form :model="form">
        <a-form-item>
          <a-input size="large" v-model:value="form.username" placeholder="用户名">
            <template #prefix>
              <UserOutlined style="margin-right: 10px" />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input type="password" size="large" v-model:value="form.password" placeholder="密码">
            <template #prefix>
              <LockOutlined style="margin-right: 10px" />
            </template>
          </a-input>
        </a-form-item>

        <a-button type="primary" size="large" block @click="onSubmit" :loading="loading">登录</a-button>
      </a-form>

<!--      <div class="auth-footer">-->
<!--        <a-row type="flex" justify="space-between">-->
<!--          <a-col :span="4" style="text-align: left">-->
<!--            <router-link class="auth-link" to="/auth/register">立即注册</router-link>-->
<!--          </a-col>-->
<!--          <a-col :span="4" style="text-align: right">-->
<!--            <router-link class="auth-link" to="/auth/forget">忘记密码</router-link>-->
<!--          </a-col>-->
<!--        </a-row>-->
<!--      </div>-->

    </a-card>
  </div>
</template>

<script>
import {defineComponent, reactive, toRaw, ref} from 'vue'
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue'
import { notification } from 'ant-design-vue'
import {loginWithPassword} from '@api/auth'
import {useRouter} from 'vue-router'
import token from '@utils/token'

export default defineComponent({
  components: {
    UserOutlined,
    LockOutlined,
  },
  setup() {
    const loading = ref(false)
    const router = useRouter()
    const form = reactive({
      username: '',
      password: '',
    });

    const onSubmit = () => {
      loading.value = true
      loginWithPassword(toRaw(form)).then((data) => {
        token.setToken(data.token, data.expired_in)
        notification['success']({message: '登录成功'})
        router.push({name: '仪表盘'})
      }).catch(({data}) => {
        notification['error']({message: '登录失败', description: data.message})
      }).finally(() => {
        loading.value = false
      })
    };

    return {
      form,
      loading,
      onSubmit,
    };
  }
})
</script>

<style scoped>

</style>
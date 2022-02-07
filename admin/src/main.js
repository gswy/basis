import { createApp } from 'vue'
import 'ant-design-vue/dist/antd.css'
import '@assets/style/style.css'
import Antd from 'ant-design-vue'
import App from './App.vue'
import router from '@router'
import store from '@store'

const app = createApp(App)

app.use(store)      // 引入store
app.use(Antd)       // 引入Antd
app.use(router)     // 引入路由

app.mount('#app')